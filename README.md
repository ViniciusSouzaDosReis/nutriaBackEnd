# nutrI.A

API da nutrI.A

### Endpoints

- Usuario
  - [Cadastrar](#cadastrar-usuario)
  - [Listar Unico](#listar-um-unico-usuario)
  - [Editar](#editar-usuario)
  - [Deletar](#deletar-usuario)
  - [Adicionar Lista de Exercicio](#criar-lista-exercicios)
  - [Apagar Lista de Exercicio](#remover-lista-de-exercicio)
  - [Adicionar Exercicio a Lista](#adicionar-exercicio)
  - [Remover Exercicio](#remover-exercicio-da-lista-de-exercicio)

- Exercicio
  - [Criar](#criar-exercicio)
  - [Acessar](#acessar-exercicio)
  - [Deletar](#deletar-exercicio)

- Receita
  - [Criar](#criar-receita)
  - [Acessar](#acessar-receita)
  - [Deletar](#deletar-receita)

- Ingrediente

### Cadastrar usuario

`POST` /nutria/api/user/register

**Campos de requisição:**

| Campo        | Tipo   | Obrigatório | Descrição                 |
|--------------|--------|:-----------:|---------------------------|
| id           | long   |     sim     | Ide gerado altomaticamente|
| name         | String |     sim     | Nome do usuário           |
| email        | String |     sim     | Email do usuário          |
| password     | String |     sim     | Senha do usuário          |
| DateOfBirth  | String |     sim     | Data de nascimento do usuário (no formato "dd/MM/yyyy") |
| Gender       | String |     sim     | Gênero do usuário         |
| Height       | double |     sim     | Altura do usuário         |
| Weight       | double |     sim     | Peso do usuário           |
| Goal         | String |     sim     | Objetivo do usuário com o aplicativo |
| ActivityLevel| String |     sim     | Nível de atividade do usuário |

```json
{
  "name": "Cleiton",
  "email": "cleiton@gmail.com",
  "password": "Cleiton123",
  "DateOfBirth": "01/05/2003",
  "Gender": "M",
  "Height": 1.73,
  "Weight": 71.32,
  "Goal": "Perder Peso",
  "ActivityLevel": "Moderado"
}
```

*Resposta*

| código | descrição 
|--------|----------
|201| retorna o campo de requisição junto
|400| Campos inválidos

### Login User

`POST` /nutria/api/user/login

**Campos de requisição:**

| Campo        | Tipo   | Obrigatório | Descrição                 |
|--------------|--------|:-----------:|---------------------------|
| email        | String |     sim     | Email do usuário          |
| password     | String |     sim     | Senha do usuário          |

```json
{
  "email": "jane.smith@example.com",
  "senha": "strongpassword"
}
```

*Resposta*

| código | descrição 
|--------|----------
|200| Retorna o JWT do usuario
|403| Campos inválidos

### Listar Um Unico Usuario

`GET` /nutria/api/user/{idUsuario}

```json
{
  "name": "Cleiton",
  "email": "cleiton@gmail.com",
  "password": "Cleiton123",
  "DateOfBirth": "01/05/2003",
  "Gender": "M",
  "Height": 1.73,
  "Weight": 71.32,
  "Goal": "Perder Peso",
  "ActivityLevel": "Moderado"
}
```

*Resposta*

| código | descrição 
|--------|----------
|200| Os dados foram retornados
|404| Não foi encontrada usuario com esse ID

### Editar Usuario

`PUT` /nutria/api/user/{idUsuario}
*Campos de requisição*

| Campo | Tipo | Obrigatório | Descrição 
|-------|------|:-------------:|----------
| id           | long   |     sim     | Ide gerado altomaticamente|
| name         | String |     sim     | Nome do usuário           |
| email        | String |     sim     | Email do usuário          |
| password     | String |     sim     | Senha do usuário          |
| DateOfBirth  | String |     sim     | Data de nascimento do usuário (no formato "dd/MM/yyyy") |
| Gender       | String |     sim     | Gênero do usuário         |
| Height       | double |     sim     | Altura do usuário         |
| Weight       | double |     sim     | Peso do usuário           |
| Goal         | String |     sim     | Objetivo do usuário com o aplicativo |
| ActivityLevel| String |     sim     | Nível de atividade do usuário |

```json
{
  "name": "Cleiton",
  "email": "cleiton@gmail.com",
  "password": "Cleiton123",
  "DateOfBirth": "01/05/2003",
  "Gender": "M",
  "Height": 1.73,
  "Weight": 71.32,
  "Goal": "Perder Peso",
  "ActivityLevel": "Moderado"
}
```
*Campos de resposta*
|codigo | descricao
|-------|----------
|200 | edicao do usuário autorizada
|400 | erro ao editar o usuário


### Deletar Usuario

`DLETE` /nutria/api/user/{idUsuario}

*Campos de requisição*
| Campo | Tipo | Obrigatório | Descrição 
|-------|------|:-------------:|----------
|id| int| sim| passado por parametro

*Campos da resposta*
|codigo | descricao
|-------|----------
|200 | o usuário foi excluido com sucesso
|400 | erro ao excluir o usuário

### Criar Lista Exercicios
`POST` /nutria/api/user/{idUsuario}/exerciceList

**Campos de requisição:**
| Campo         | Tipo   | Obrigatório | Descrição                                       |
|---------------|--------|:-----------:|-------------------------------------------------|
| id            | long   |     Sim     | ID gerado automaticamente                        |
| name          | String |     Sim     | Nome da lista de exercícios                     |
| description   | String |     Sim     | Descrição da lista de exercícios                |
| exercices     | Array  |     Sim     | Lista de exercícios da lista                    |

```json
{
  {
  "name": "Minha Lista de Exercícios",
  "description": "Lista de exercícios para treino",
  "exercices": [
    {
      "name": "Exercício 1",
      "description": "Descrição do exercício 1",
      "caloriesBurnedPerMinute": 10.5
    }
  ]
  }

}
```

*Resposta*

| código | descrição 
|--------|----------
|201| Lista criada
|400| Campos inválidos

### Adicionar Exercicio

`POST`/nutria/api/user/{userId}/exercicelist/{exerciceListId}/exercice

**Campos de requisição**
| Campo                  | Tipo   | Obrigatório | Descrição                                  |
|------------------------|--------|:-----------:|--------------------------------------------|
| name                   | String |     sim     | Nome do exercício                          |
| description            | String |     sim     | Descrição do exercício                     |
| caloriesBurnedPerMinute| double |     sim     | Calorias queimadas por minuto durante o exercício |


```JSON
{
  "name": "Exercício 1",
  "description": "Descrição do exercício 1",
  "caloriesBurnedPerMinute": 10.5
}
```

*Resposta*

| código | descrição 
|--------|----------
|201| Exercicio Adicionado
|400| Campos inválidos

### Remover Exercicio da Lista de Exercicio

`DELETE`/nutria/api/user/{userId}/exercicelist/{exerciceListId}/exercice/{exerciceId}

*Campos da resposta*
|codigo | descricao
|-------|----------
|204  | o exercicio foi excluido com sucesso
|400 | erro ao excluir o exercicio


### Remover Lista de Exercicio

`DELETE`/nutria/api/user/{userId}/exercicelist/{exerciceListId}

*Campos da resposta*
|codigo | descricao
|-------|----------
|204  | a lista foi excluida com sucesso
|400 | erro ao excluir a lista

### Adicionar Lista de Receitas

`POST`/nutria/api/user/{userId}/recipelist

**Campos de requisição**
| Campo          | Tipo    | Obrigatório | Descrição                                                        |
|----------------|---------|:-----------:|------------------------------------------------------------------|
| name         | String  |     sim     | Nome da lista de receitas.                                       |
| description  | String  |     sim     | Descrição da lista de receitas.                                  |
| recipes      | Array   |     sim     | Lista de objetos de receita contendo as informações das receitas. |

Cada objeto de receita possui os seguintes campos:

| Campo          | Tipo    | Obrigatório | Descrição                                                        |
|----------------|---------|:-----------:|------------------------------------------------------------------|
| name         | String  |     sim     | Nome da receita.                                                 |
| description  | String  |     sim     | Descrição da receita.                                            |
| ingredients  | Array   |     sim     | Lista de objetos de ingrediente contendo as informações dos ingredientes. |

Cada objeto de ingrediente possui os seguintes campos:

| Campo          | Tipo    | Obrigatório | Descrição                                                        |
|----------------|---------|:-----------:|------------------------------------------------------------------|
| name         | String  |     sim     | Nome do ingrediente.                                             |
| quantity     | Number  |     sim     | Quantidade do ingrediente.                                       |


```JSON
{
  "name": "Minha Lista de Receitas",
  "description": "Lista de receitas para experimentar",
  "recipes": [
    {
      "name": "Receita 1",
      "description": "Descrição da receita 1",
      "ingredients": [
        {
          "name": "Ingrediente 1",
          "quantity": 2
        },
        {
          "name": "Ingrediente 2",
          "quantity": 1
        }
      ]
    },
    {
      "name": "Receita 2",
      "description": "Descrição da receita 2",
      "ingredients": [
        {
          "name": "Ingrediente 3",
          "quantity": 3
        },
        {
          "name": "Ingrediente 4",
          "quantity": 1
        }
      ]
    }
  ]
}
```

*Resposta*

| código | descrição 
|--------|----------
|201| Lista criada
|400| Campos inválidos

### Adicionar Receitas

`POST`/nutria/api/user/{userId}/recipelist/{recipeListId}/recipe

| Campo          | Tipo    | Obrigatório | Descrição                                                        |
|----------------|---------|:-----------:|------------------------------------------------------------------|
| name         | String  |     sim     | Nome da receita.                                                 |
| description  | String  |     sim     | Descrição da receita.                                            |
| ingredients  | Array   |     sim     | Lista de objetos de ingrediente contendo as informações dos ingredientes. |

Cada objeto de ingrediente possui os seguintes campos:

| Campo          | Tipo    | Obrigatório | Descrição                                                        |
|----------------|---------|:-----------:|------------------------------------------------------------------|
| name         | String  |     sim     | Nome do ingrediente.                                             |
| quantity     | Number  |     sim     | Quantidade do ingrediente.                                       |


```JSON
{
  "name": "Receita 1",
  "description": "Descrição da receita 1",
  "ingredients": [
    {
      "name": "Ingrediente 1",
      "quantity": 2
    },
    {
      "name": "Ingrediente 2",
      "quantity": 1
    }
  ]
}
```

*Resposta*

| código | descrição 
|--------|----------
|201| Receita Adicionado
|400| Campos inválidos

### Remover Receita da Lista de Receita

`DELETE`/nutria/api/user/{userId}/recipelist/{recipeListId}/recipe/{recipeId}

*Campos da resposta*
|codigo | descricao
|-------|----------
|204  | a receita foi excluida com sucesso
|400 | erro ao excluir receita


### Remover Lista de Exercicio

`DELETE`/nutria/api/user/{userId}/recipelist/{recipeListId}

*Campos da resposta*
|codigo | descricao
|-------|----------
|204  | a lista foi excluida com sucesso
|400 | erro ao excluir a lista


### Criar Exercicio

`POST`/nutria/api/exercice

**Campos de Requisição**

| Campo                    | Tipo    | Obrigatório | Descrição                                       |
|--------------------------|---------|:-----------:|-------------------------------------------------|
| name                     | String  |     Sim     | Nome do exercício                               |
| description                | String  |     Sim     | Descrição do exercício                          |
| caloriesBurnedPerMinute  | double  |     Sim     | Quantidade de calorias queimadas por minuto      |


```json
{
  "name": "Corrida",
  "description": "Exercício aeróbico que envolve correr",
  "caloriesBurnedPerMinute": 10.5,
}
```
*Resposta*

| código | descrição 
|--------|----------
|201| Exercicio Criado
|400| Campos inválidos

### Acessar Exercicio
`GET`/nutria/api/exercice/{idExercice}

```json
{
  "name": "Corrida",
  "description": "Exercício aeróbico que envolve correr",
  "caloriesBurnedPerMinute": 10.5,
}
```
*Campos da resposta*
|codigo | descricao
|-------|----------
|200 | campos do exercicio
|400 | exercicio não encontrado

### Deletar Exercicio

`DELETE` /nutria/api/exercice/{idExercice}

*Campos da resposta*
|codigo | descricao
|-------|----------
|204 | exercicio excluido com sucesso
|400 | erro ao excluir o exercicio

### Criar Receita
`POST`/nutria/api/recipe

**Campos de requisição**

| Campo          | Tipo     | Obrigatório | Descrição                                     |
|----------------|----------|:-----------:|----------------------------------------------|
| name           | String   |     sim     | Nome da receita                               |
| description    | String   |     sim     | Descrição da receita                          |
| ingredients    | [Object] |     sim     | Lista de ingredientes da receita              |
| recipeLists    | [Object] |     sim     | Lista de listas de receitas relacionadas       |


```json
{
  "name": "Bolo de Chocolate",
  "description": "Delicioso bolo de chocolate",
  "ingredients": [
    {"id": 1},
    {"id": 2},
    {"id": 3}
  ],
  "recipeLists": [
    {"id": 1},
    {"id": 2}
  ]
}
```

*Campos da resposta*
|codigo | descricao
|-------|----------
|201 | Receita criada 
|400 | Erro ao criar Receita

### Acessar Rceita
`GET`/nutria/api/recipe/{idRecipe}

```json
{
  "name": "Bolo de Chocolate",
  "description": "Delicioso bolo de chocolate",
  "ingredients": [
    {"id": 1},
    {"id": 2},
    {"id": 3}
  ],
  "recipeLists": [
    {"id": 1},
    {"id": 2}
  ]
}
```

*Campos da resposta*
|codigo | descricao
|-------|----------
|200 | corpo da receita
|400 | não encontrou a receita

### Deletar Receita

### Criar Ingrediente
`POST`/nutria/api/ingredient

**Campos de requisição**

| Campo          | Tipo    | Obrigatório | Descrição                                      |
|----------------|---------|-------------|------------------------------------------------|
| id             | int     | sim         | Identificador único do ingrediente             |
| name           | String  | sim         | Nome do ingrediente                            |
| foodGroup      | String  | sim         | Grupo de alimentos ao qual o ingrediente pertence |
| calories       | int     | sim         | Quantidade de calorias por porção              |
| carbohydrates  | double  | sim         | Quantidade de carboidratos por porção          |
| protein        | double  | sim         | Quantidade de proteínas por porção             |
| fat            | double  | sim         | Quantidade de gorduras por porção              |
| servingSize    | String  | sim         | Tamanho da porção                               |
| measurementUnit| String  | sim         | Unidade de medida do ingrediente                |
| isOrganic      | boolean | sim         | Indica se o ingrediente é orgânico              |
| isGlutenFree   | boolean | sim         | Indica se o ingrediente é livre de glúten       |
| isVegetarian   | boolean | sim         | Indica se o ingrediente é adequado para vegetarianos |
| isVegan        | boolean | sim         | Indica se o ingrediente é adequado para veganos  |



```json
{
  "name": "Morango",
  "foodGroup": "Frutas",
  "calories": 100,
  "carbohydrates": 10.5,
  "protein": 5.2,
  "fat": 3.8,
  "servingSize": "100g",
  "measurementUnit": "grams",
  "isOrganic": true,
  "isGlutenFree": false,
  "isVegetarian": true,
  "isVegan": true
}

```

*Campos da resposta*
|codigo | descricao
|-------|----------
|201 | Ingrediente criado 
|400 | Erro ao criar Ingrediente

### Acessar Rceita
`GET`/nutria/api/ingredient/{idIngredient}

```json
{
  "name": "Morango",
  "foodGroup": "Frutas",
  "calories": 100,
  "carbohydrates": 10.5,
  "protein": 5.2,
  "fat": 3.8,
  "servingSize": "100g",
  "measurementUnit": "grams",
  "isOrganic": true,
  "isGlutenFree": false,
  "isVegetarian": true,
  "isVegan": true
}
```

*Campos da resposta*
|codigo | descricao
|-------|----------
|200 | corpo do ingrediente
|400 | não encontrou o ingrediente

### Deletar Ingrediente

`DELETE` /nutria/api/ingredient/{idIngredient}

*Campos da resposta*
|codigo | descricao
|-------|----------
|204 | ingrediente excluido com sucesso
|400 | erro ao excluir o ingrediente