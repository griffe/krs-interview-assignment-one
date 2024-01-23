# How to start the application
Go to terminal, navigate to the root directory and execute

```
.\gradlew bootRun
```

# How to run from docker
Go to terminal, navigate to the root directory, build image:
```
.\docker build -t interview:assignment-one .

```

Run image:
```
docker run -p 8080:8080 interview:assignment-one .
```

# How to test the application

Bunch of helpful requests:
Unauthorized request:

```
curl -v "localhost:8080/calculate/1ace7cb8-60be-4442-b88b-7bd7514c5ecd/1/?discountType=PERCENTAGE&discountPercentage=100"
```

Authorized request, bad parameters:
```
curl -v -H "X-API-KEY: ultraSecure" -X GET "localhost:8080/calculate/1ace7cb8-60be-4442-b88b-7bd7514c5ecd/1/?discountType=MYSTIC&discountPercentage=100"

```

Successful request, Percentage discount:
```
curl -v -H "X-API-KEY: ultraSecure" -X GET "localhost:8080/calculate/1ace7cb8-60be-4442-b88b-7bd7514c5ecd/1/?discountType=PERCENTAGE&discountPercentage=100"
```

Successful request, Amount based discount:
```
curl -v -H "X-API-KEY: ultraSecure" -X GET "localhost:8080/calculate/1ace7cb8-60be-4442-b88b-7bd7514c5ecd/1/?discountType=AMOUNT"
```

# Configuration
[Configuration file](assignment-one/src/main/resources/application.yml)