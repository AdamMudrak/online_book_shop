# Run The Application

## Important

Local `.env.sample` values are intentionally dummy values. They are enough for
the application to start locally, run migrations, and test most non-email flows.

Email delivery requires real Resend credentials. Without real credentials, the
application can still run locally, but registration/password email flows will not
send real messages.

## Backend: database + application

From `online_book_shop`:

```bash
docker-compose --env-file .env.sample up --build
```

Backend URL:

```text
http://localhost:8080
```

Swagger/OpenAPI URL:

```text
http://localhost:8080/swagger-ui/index.html
```

Local limitations:

- email sending uses dummy Resend values unless you replace them;
- links generated in email flows use `SERVER_PATH`;
- CORS is configured from `SERVER_PATH` in the current application code.

## Stop all

From `online_book_shop`:

```bash
docker-compose --env-file .env.sample down
```

## Stop all and delete database volume

From `online_book_shop`:

```bash
docker-compose --env-file .env.sample down -v
```
