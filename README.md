# Manual

## Passos para subir o ms localmente

- Instale o lombok na sua IDE, segue um passo a passo caso necessite: https://dicasdejava.com.br/como-configurar-o-lombok-no-eclipse/

- Crie uma database no mysql com o seguinte nome financaspessoais

- No properties.yml altere o ip:porta para o ip e porta do seu banco.

## Passos para acessar a documentação

- Após seguir os passoas acima e subir o MS, acesse http://ipDoServiço:Porta/swagger-ui.html#/ no meu caso é: http://localhost:8080/swagger-ui.html#/

## Passos para chamadas das APIs

#### Primeiro chame a api para criar uma account no path post /financaspessoais/v1/account com o seguinte payload
``` 
{
    "user" : {
        "name" : "seuNome",
        "email" : "seuEmail@teste.com"
    }
} 
```
O e-mail informado deve ser um e-mail real.

A api acima retornará o seguinte:
``` 
{
    "idAccounts": 1,
    "user": {
        "name": "seuNome",
        "email": "seuEmail@teste.com"
    },
    "balance": 0.0
}
```
Com o **idAccounts** você conseguirá efetuar todas as transações e consultas:

##### Income POST - /financaspessoais/v1/trasaction/{idAccounts}/income
``` 
{
    "value" : "1000"
}
```
Retorno: 204 - No content

##### Income POST - /financaspessoais/v1/trasaction/{idAccounts}/expense
``` 
{
    "value" : "362.93"
}
```
Retorno: 204 - No content

##### Balance GET - financaspessoais/v1/{idAccounts}/balance:

Exemplo do retorno:
``` 
{
    "balance": "R$ 637,07",
    "date": "2021-05-13T13:22:07.295"
}
```
##### Extract GET - /financaspessoais/v1/1/extract

Exemplo do retorno:
``` 
{
    "transactions": [
        {
            "value": "R$ 1.000,00",
            "date": "2021-05-13T13:21:50",
            "type": "Entrada"
        },
        {
            "value": "R$ 362,93",
            "date": "2021-05-13T13:22:03",
            "type": "Saida"
        }
    ]
}
```

**OBS:** *Você também consegue chamar as APIs através da documentação, basta acessar http://ipDoServiço:Porta/swagger-ui.html#/*
