# API de Controle de Arquivos

## 📋 Descrição

Esta é uma API REST desenvolvida em Spring Boot para gerenciamento de arquivos. A aplicação permite fazer upload, download, listagem, atualização e exclusão de arquivos, armazenando-os em um banco de dados H2 em memória.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.3**
- **Spring Data JPA**
- **H2 Database** (banco em memória)
- **Maven**
- **Spring Validation**

## 📁 Estrutura do Projeto

```
ApiServer/
├── src/main/java/com/gabriel/project/
│   ├── APIApplication.java              # Classe principal da aplicação
│   ├── Config/
│   │   └── CorsConfig.java             # Configuração CORS
│   ├── controller/
│   │   └── ApiController.java          # Controlador REST
│   ├── model/
│   │   ├── FileEntity.java             # Entidade JPA
│   │   ├── FileDto.java                # DTO para transferência de dados
│   │   └── UploadDto.java              # DTO para upload
│   ├── repository/
│   │   └── ArquivoEntityRepository.java # Repositório JPA
│   └── service/
│       ├── ServiceUpload.java          # Serviço de upload
│       ├── ServiceDownload.java        # Serviço de download
│       ├── ServiceDelete.java          # Serviço de exclusão
│       ├── ServiceUpdate.java          # Serviço de atualização
│       └── ServiceSearchForAll.java    # Serviço de listagem
├── src/main/resources/
│   ├── application.properties          # Configurações da aplicação
│   └── paginaBonita.html              # Página de teste
└── pom.xml                            # Dependências Maven
```

## 🛠️ Configuração e Instalação

### Pré-requisitos

- Java 21 ou superior
- Maven 3.6+

### Como executar

1. **Clone o repositório:**

   ```bash
   git clone <url-do-repositorio>
   cd ApiServer
   ```

2. **Execute a aplicação:**

   ```bash
   ./mvnw spring-boot:run
   ```

   ou

   ```bash
   mvn spring-boot:run
   ```

3. **Acesse a aplicação:**
   - API: http://localhost:8080
   - Console H2: http://localhost:8080/h2-console

## 📊 Banco de Dados

A aplicação utiliza o **H2 Database** em modo memória com as seguintes configurações:

- **URL:** `jdbc:h2:mem:testdb`
- **Usuário:** `teste`
- **Senha:** (vazia)
- **Console H2:** http://localhost:8080/h2-console

### Estrutura da Tabela

```sql
CREATE TABLE file_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    type VARCHAR(255),
    file BLOB
);
```

## 🔌 Endpoints da API

### Base URL

```
http://localhost:8080/api
```

### 1. Listar Todos os Arquivos

```http
GET /api/files
```

**Resposta:**

```json
[
  {
    "name": "arquivo.pdf",
    "type": "application/pdf",
    "file": null
  }
]
```

### 2. Upload de Arquivo

```http
POST /api/upload
Content-Type: multipart/form-data
```

**Parâmetros:**

- `file`: Arquivo a ser enviado
- `name_file`: Nome do arquivo

**Resposta:**

```
arquivo salvo
http://localhost:8080/api/download/arquivo.pdf
```

### 3. Download de Arquivo

```http
GET /api/download/{name}
```

**Parâmetros:**

- `name`: Nome do arquivo

**Resposta:** Arquivo binário com o tipo MIME apropriado

### 4. Atualizar Arquivo

```http
PUT /api/update/{name}
Content-Type: multipart/form-data
```

**Parâmetros:**

- `name`: Nome do arquivo a ser atualizado
- `file`: Novo arquivo
- `name_file`: Novo nome do arquivo

**Resposta:**

```json
{
  "name": "novo_arquivo.pdf",
  "type": "application/pdf",
  "file": null
}
```

### 5. Excluir Arquivo

```http
DELETE /api/delete/{name}
```

**Parâmetros:**

- `name`: Nome do arquivo a ser excluído

**Resposta:**

```json
{
  "name": "arquivo.pdf",
  "type": "application/pdf",
  "file": null
}
```

## 🔧 Configurações

### application.properties

```properties
# Configurações básicas
spring.application.name=upload

# Configurações do banco H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=teste
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# Console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Limite de upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

### CORS

A aplicação está configurada para aceitar requisições do frontend em `http://localhost:3000`:

```java
.allowedOrigins("http://localhost:3000")
.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
.allowedHeaders("*")
.allowCredentials(true)
```

## 📝 Modelos de Dados

### FileEntity

```java
@Entity
@Table(name = "file_entity", uniqueConstraints = {@UniqueConstraint(name = "unique_name", columnNames = "name")})
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Lob
    private byte[] file;
}
```

### FileDto

```java
public record FileDto(String name, String type, byte[] file) {
    public FileDto(String nameFile, String typeFile){
        this(nameFile, typeFile, null);
    }
}
```

### UploadDto

```java
public record UploadDto(MultipartFile file, String name_file) {
}
```

## 🧪 Testando a API

### Usando cURL

1. **Upload de arquivo:**

   ```bash
   curl -X POST http://localhost:8080/api/upload \
     -F "file=@/caminho/para/arquivo.pdf" \
     -F "name_file=meu_arquivo.pdf"
   ```

2. **Listar arquivos:**

   ```bash
   curl http://localhost:8080/api/files
   ```

3. **Download de arquivo:**

   ```bash
   curl http://localhost:8080/api/download/meu_arquivo.pdf -o arquivo_baixado.pdf
   ```

4. **Excluir arquivo:**
   ```bash
   curl -X DELETE http://localhost:8080/api/delete/meu_arquivo.pdf
   ```

### Usando Postman

1. Configure a URL base: `http://localhost:8080/api`
2. Para uploads, use `multipart/form-data` com os campos `file` e `name_file`
3. Para downloads, o arquivo será retornado como resposta binária

## ⚠️ Limitações e Considerações

- **Tamanho máximo:** 10MB por arquivo
- **Armazenamento:** Banco H2 em memória (dados são perdidos ao reiniciar)
- **Segurança:** Não há autenticação implementada
- **Produção:** Não recomendado para uso em produção sem modificações

## 🔄 Funcionalidades Implementadas

- ✅ Upload de arquivos
- ✅ Download de arquivos
- ✅ Listagem de todos os arquivos
- ✅ Atualização de arquivos
- ✅ Exclusão de arquivos
- ✅ Validação de dados
- ✅ Configuração CORS
- ✅ Console H2 para visualização do banco

## 🚀 Próximos Passos Sugeridos

1. **Implementar autenticação e autorização**
2. **Adicionar logs estruturados**
3. **Implementar testes unitários e de integração**
4. **Configurar banco de dados persistente (PostgreSQL, MySQL)**
5. **Adicionar compressão de arquivos**
6. **Implementar cache para melhor performance**
7. **Adicionar documentação com Swagger/OpenAPI**
8. **Configurar monitoramento e métricas**

**Desenvolvido por Gabriel Souza Silva**
