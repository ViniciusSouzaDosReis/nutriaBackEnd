package com.octadev.nutria.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octadev.nutria.models.Credential;
import com.octadev.nutria.models.Exercice;
import com.octadev.nutria.models.ExerciceList;
import com.octadev.nutria.models.Recipe;
import com.octadev.nutria.models.RecipeList;
import com.octadev.nutria.models.User;
import com.octadev.nutria.models.Token;
import com.octadev.nutria.repository.ExerciceListRepository;
import com.octadev.nutria.repository.ExerciceRepository;
import com.octadev.nutria.repository.RecipeListRepository;
import com.octadev.nutria.repository.RecipeRepository;
import com.octadev.nutria.repository.UserRepository;
import com.octadev.nutria.service.ChatGPTApiClient;
import com.octadev.nutria.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    RecipeListRepository recipeListRepository;
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    ExerciceListRepository exerciceListRepository;
    @Autowired
    ExerciceRepository exerciceRepository;

    @Autowired
    UserRepository repository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @GetMapping
    public List<User> index() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        return ResponseEntity.ok(getUser(id));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registrar(@RequestBody @Valid User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        String promptRecipe = "Gerar receitas com base nas informações do usuário";
        String contentRecipe = "Peso: " + user.getWeight() + ", Altura: " + user.getHeight();
        String promptExercice = "Gerar exercicios com base nas informações do usuário";
        String contentExercice = "Peso: " + user.getWeight() + ", Altura: " + user.getHeight();

        String responseRecipe = ChatGPTApiClient.callChatGPTAPI(promptRecipe, contentRecipe);
        String responseExercice = ChatGPTApiClient.callChatGPTAPI(promptExercice, contentExercice);

        List<Recipe> recipes = processResponse(responseRecipe, Recipe.class);
        createRecipeList(user, recipes);

        List<Exercice> exercices = processResponse(responseExercice, Exercice.class);
        createExerciceList(user, exercices);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Credential credential) {
        manager.authenticate(credential.toAuthentication());
        var token = tokenService.generateToken(credential);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/{userId}/exercicelist")
    public ResponseEntity<ExerciceList> createExerciceList(@PathVariable Long userId,
            @RequestBody @Valid ExerciceList exerciceList) {
        User user = getUser(userId);
        exerciceList.setUser(user);

        List<Exercice> exercices = exerciceList.getExercices();
        for (Exercice exercice : exercices) {
            exercice.setExerciceList(exerciceList);
        }

        for (Exercice exercice : exercices) {
            exerciceRepository.save(exercice);
        }

        exerciceListRepository.save(exerciceList);
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciceList);
    }

    @PostMapping("/{userId}/exercicelist/{exerciceListId}/exercice")
    public ResponseEntity<ExerciceList> addExerciceToList(@PathVariable Long userId, @PathVariable Long exerciceListId,
            @RequestBody @Valid Exercice exercice) {
        User user = getUser(userId);
        ExerciceList exerciceList = getExerciceList(exerciceListId, user);
        exerciceList.getExercices().add(exercice);
        exerciceListRepository.save(exerciceList);
        return ResponseEntity.ok(exerciceList);
    }

    @DeleteMapping("/{userId}/exercicelist/{exerciceListId}/exercice/{exerciceId}")
    public ResponseEntity<ExerciceList> removeExerciceFromList(@PathVariable Long userId,
            @PathVariable Long exerciceListId,
            @PathVariable Long exerciceId) {
        User user = getUser(userId);
        ExerciceList exerciceList = getExerciceList(exerciceListId, user);
        exerciceList.getExercices().removeIf(exercice -> exercice.getId().equals(exerciceId));

        exerciceListRepository.save(exerciceList);
        return ResponseEntity.ok(exerciceList);
    }

    @DeleteMapping("/{userId}/exercicelist/{exerciceListId}")
    public ResponseEntity<Void> deleteExerciceList(@PathVariable Long userId, @PathVariable Long exerciceListId) {
        User user = getUser(userId);
        ExerciceList exerciceList = getExerciceList(exerciceListId, user);
        user.getExerciceLists().remove(exerciceList);
        repository.save(user);

        exerciceListRepository.delete(exerciceList);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/recipelist")
    public ResponseEntity<RecipeList> createRecipeList(@PathVariable Long userId,
            @RequestBody @Valid RecipeList recipeList) {
        User user = getUser(userId);
        recipeList.setUser(user);

        List<Recipe> recipes = recipeList.getRecipes();
        for (Recipe recipe : recipes) {
            recipe.setRecipeList(recipeList);
        }
        for (Recipe recipe : recipes) {
            recipeRepository.save(recipe);
        }

        recipeListRepository.save(recipeList);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeList);
    }

    @PostMapping("/{userId}/recipelist/{recipeListId}/recipe")
    public ResponseEntity<RecipeList> addRecipeToList(@PathVariable Long userId, @PathVariable Long recipeListId,
            @RequestBody @Valid Recipe recipe) {
        User user = getUser(userId);
        RecipeList recipeList = getRecipeList(recipeListId, user);
        recipeList.getRecipes().add(recipe);
        recipeListRepository.save(recipeList);
        return ResponseEntity.ok(recipeList);
    }

    @DeleteMapping("/{userId}/recipelist/{recipeListId}/recipe/{recipeId}")
    public ResponseEntity<RecipeList> removeRecipeFromList(@PathVariable Long userId,
            @PathVariable Long recipeListId,
            @PathVariable Long recipeId) {
        User user = getUser(userId);
        RecipeList recipeList = getRecipeList(recipeListId, user);
        recipeList.getRecipes().removeIf(recipe -> recipe.getId().equals(recipeId));

        recipeListRepository.save(recipeList);
        return ResponseEntity.ok(recipeList);
    }

    @DeleteMapping("/{userId}/recipelist/{recipeListId}")
    public ResponseEntity<Void> deleteRecipeList(@PathVariable Long userId, @PathVariable Long recipeListId) {
        User user = getUser(userId);
        RecipeList recipeList = getRecipeList(recipeListId, user);
        user.getRecipeLists().remove(recipeList);
        repository.save(user);

        recipeListRepository.delete(recipeList);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> destroy(@PathVariable Long id) {
        repository.delete(getUser(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid User user) {
        getUser(id);
        user.setId(id);
        repository.save(user);
        return ResponseEntity.ok(user);
    }

    private User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "exercicio não encontrada"));
    }

    private ExerciceList getExerciceList(Long exerciceListId, User user) {
        return user.getExerciceLists().stream()
                .filter(list -> list.getId().equals(exerciceListId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de exercícios não encontrada"));
    }

    private RecipeList getRecipeList(Long recipeListId, User user) {
        return user.getRecipeLists().stream()
                .filter(list -> list.getId().equals(recipeListId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de receitas não encontrada"));
    }

    private <T> List<T> processResponse(String response, Class<T> responseType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(response);
            JsonNode choices = responseJson.get("choices");

            if (choices != null && choices.isArray()) {
                List<T> items = new ArrayList<>();

                for (JsonNode choice : choices) {
                    T item = objectMapper.treeToValue(choice, responseType);
                    items.add(item);
                }

                return items;
            } else {
                return Collections.emptyList();
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void createRecipeList(User user, List<Recipe> recipes) {
        RecipeList recipeList = new RecipeList();
        recipeList.setUser(user);
        recipeList.setRecipes(recipes);

        if (user.getRecipeLists() == null) {
            user.setRecipeLists(new ArrayList<>());
        }
        user.getRecipeLists().add(recipeList);

        repository.save(user);
    }

    private void createExerciceList(User user, List<Exercice> exercices) {
        ExerciceList exerciceList = new ExerciceList();
        exerciceList.setUser(user);
        exerciceList.setExercices(exercices);

        if (user.getExerciceLists() == null) {
            user.setExerciceLists(new ArrayList<>());
        }
        user.getExerciceLists().add(exerciceList);

        repository.save(user);
    }

}
