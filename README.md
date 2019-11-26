# WriterPad

## Step 1:  Create Spring Boot application

Cover how Spring Boot works under the hood

## Step 2: DIY: Add Checkstyle and Spotbugs to the project

- [ ] Integrate Checkstyle
- [ ] Integrate Spotbugs
- [ ] Integrate CircleCI



## Step 3: REST API to create an article

`POST /api/articles`

Example request body:

```JSON
{
    "title": "How to learn Spring Booot",
    "description": "Ever wonder how?",
    "body": "You have to believe",
    "tags": ["java", "Spring Boot", "tutorial"]
}
```

Required fields: `title`, `description`, `body`

Optional fields: `tags` as an array of Strings, and `featuredImage`

Tags should be saved as lowercase.

Response

1. It will return response code 201 if article is created successfully

2. It will return 400 if validation failed

3. It will return 500 if anything else happened

4. It will return created article as shown below.

   ```json
   {
       "id": "a98fd991e69a",
       "slug": "how-to-learn-spring-boot",
       "title": "How to learn Spring Booot",
       "description": "Ever wonder how?",
       "body": "You have to believe",
       "tags": ["java", "spring-boot","tutorial"],
       "createdAt": "2019-11-24T03:22:56.637Z",
       "updatedAt": "2019-11-24T03:48:35.824Z",
       "favorited": false,
       "favoritesCount": 0,
   }
   ```





