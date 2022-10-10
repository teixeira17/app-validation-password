<h2>APP-VALIDATION-PASSWORD-JAVA</h2>
Foi realizada a API, para a validação de criação de senhas - Case Itaú

## :hammer: Regras para a senha

- ``Nove ou mais caracteres``
- ``Ao menos 1 dígito``
- ``Ao menos 1 letra minúscula``
- ``Ao menos 1 letra maiúscula``
- ``Ao menos 1 caractere especial``
- ``Considere como especial os seguintes caracteres: !@#$%^&*()-+``
- ``Não possuir caracteres repetidos dentro do conjunto``

Exemplo:

![image](https://user-images.githubusercontent.com/32078335/194724066-51a0cd78-e502-4e79-96aa-e85ce74d5a80.png)


## ✔️ Tecnologias e técnicas utilizadas

- ``Java 11``
- ``InteliJ IDEA``
- ``PostgreSQL 14``
- ``Paradigma de orientação a objetos``
- ``Arquitetura Clean Arch``
- ``Testes unitários com Junit utilizando Mockito``
- ``Swagger``


## 🎲 Iniciando projeto pela primeira vez
Antes de começar será necessário que a máquina possua a versão 11 do Java, o Postgres 14 e uma IDE de sua preferência.

[Tutorial para Instalação do Java 11](https://www.youtube.com/watch?v=bE5GbXgfi8c&t=758s).

[Tutorial para Instalação do Postgres 14](https://www.youtube.com/watch?v=L_2l8XTCPAE&t=302s&ab_channel=HashtagPrograma%C3%A7%C3%A3o).


- Clone este repositório
git clone https://github.com/teixeira17/app-validation-password/tree/master

- Inicie o servidor do Postgres, no pgAdmin

- Inicie a aplicação em sua IDE de preferência

- Acesse o seguinte endereço no navegador para testar a aplicação via Swagger
http://localhost:8080/swagger-ui.html

``` json
{      
        "password": "abtp9!fok"
    }
```

- Acesse o PgAdmin, e faça a seguinte consulta para verificar os registros salvos de senhas validadas com sucesso

![pgAdmin](https://user-images.githubusercontent.com/32078335/194724644-cf5e136e-a629-4f65-880c-55ce82651d9f.png)



## 🎲 Documentação da API

CURL de exemplo:


```json
`curl -X 'POST' \
  'http://localhost:8080/validation' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "password": "AbTp9!fok"
}'`
```

:white_check_mark: Imagem de exemplo do Swagger quando a senha for válida.
Senha informada: "AbTp9!fok"

![image](https://user-images.githubusercontent.com/32078335/194924823-e2b2aca6-29c6-441a-b4ff-bf3edb4bb60f.png)

:x: Imagem de exemplo do Swagger quando a senha não tiver caracter minúsculo.
Senha informada: "ABTP9!FOK"

![image](https://user-images.githubusercontent.com/32078335/194924934-af046ba8-67e0-4567-aaec-58723546f6c3.png)

:x: Imagem de exemplo do Swagger quando a senha não tiver caracter maiúsculo.
Senha informada: "abtp9!fok"

![image](https://user-images.githubusercontent.com/32078335/194925027-c7302a19-0864-48b7-ab73-63c884f9e5f6.png)

:x: Imagem de exemplo do Swagger quando a senha não tiver caracter especial.
Senha informada: "aBtp9RfoK"

![image](https://user-images.githubusercontent.com/32078335/194925114-f141f680-5ac7-4e5e-a462-5ce43ec9d41a.png)

:x: Imagem de exemplo do Swagger quando a senha não tiver caracteres suficientes.
Senha informada: "aB!"

![image](https://user-images.githubusercontent.com/32078335/194925219-75ca75d2-e277-492d-93bc-4cacdf2a7b86.png)

:x: Imagem de exemplo do Swagger quando a senha tiver caracteres repetidos.
Senha informada: "AbTp9!foA"

![image](https://user-images.githubusercontent.com/32078335/194925280-814a891a-1a58-41e1-a3b7-37494b318657.png)


## ⚡ Solução de negócio
- Primeiramente, precisei decidir qual seria a maneira mais simples para validar todos os requisitos na criação de senhas, após realizar alguns estudos sobre validações, cheguei a conclusão que seria de grande valia utilizar o recurso das Expressões Regulares(REGEX), com elas, foi possível validar todas as regras, em poucas linhas de código.
- Decidido qual maneira iria validar as senhas, iniciei a criação da API REST, usando o Java na versão 11, com Spring, utilizando boas práticas do SOLID com a arquitetura Clean Arch.
- No projeto, foi realizado apenas um controller, com um único endpoint(POST), para receber a senha(String) pelo body da requisição. Para o tratamento da senha, foi criado um Service com 4 métodos. O Primeiro método valida se a String possui algum caracter repetido, neste método a verificação é realizada de maneira manual, sem REGEX. Já o segundo método, é aonde é realizado a validação de todas as regras restantes, com auxílio das REGEX. No terceiro método, é o método principal do Service, é ele quem chama os dois primeiros métodos para validação, e se estiver tudo OK com a senha, ele chama o quarto método para fazer a encriptação da senha e já salvar no banco encriptografado.
- Para seguir e respeitar os princípios da arquitetura Clean Arch e do SOLID, o projeto foi dividido em dois pacotes principais, são eles o "api.rest" e "domain". Na camada api.rest, foram criados pacotes para o controller da aplicação, para os objetos DTO, tanto de requisições como para respostas e também foi criado um pacote para a conversão dos objetos DTO. Já na camada domain, ficou apenas as classes de negócio, ou seja, um pacote para o Service, outro para a Entity, e por fim, um para o Repository.
- A respeito das respostas da API, existem duas possíveis.. São eles o código de status 200, representando que a senha foi validada e salva no banco com sucesso. E também o código de status 400, representando que não foi possível salvar a senha, e no corpo desta resposta, possui uma mensagem com o motivo da falha na validação, dessa forma o usuário poderá corrigir sua senha de maneira mais fácil.
- Para testar a aplicação, eu utilizei o JUnit com Mockito, para realizar os testes unitários do Service, testando todos os cenários, para uma senha válida, e todas as regras para uma senha inválida. Infelizmente não consegui encontrar nenhum teste de integração que fosse válido para colocar, pro futuro irei priorizar meus estudos em testes de integração para que possa agregar em meus projetos. Ainda posso melhorar mais os testes unitários da aplicação, montando mais cenários de testes, e adicionando alguma lib para analisar a cobertura dos testes.
- Quero futuramente me aperfeiçoar ainda mais nas arquiteturas de projetos e nas boas práticas de programação, e também em serviços de nuvem, pois ainda não possuo um conhecimento avançado no recurso. Agradeço desde já a oportunidade, e fico aberto a críticas e sugestões para melhorar a API! :smile:
