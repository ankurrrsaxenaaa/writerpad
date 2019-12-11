[![CircleCI](https://circleci.com/gh/ankurrrsaxenaaa/writerpad.svg?style=svg&circle-token=612bfa71f127c957f1ba6a5553a8c8328c58ade5)](https://circleci.com/gh/ankurrrsaxenaaa/writerpad)

# WriterPad

## Day 1

### Story 1:  Create Spring Boot application

Cover how Spring Boot works under the hood

### Story 2: DIY: Add Checkstyle and Spotbugs to the project

- [ ] Integrate Checkstyle
- [ ] Integrate Spotbugs
- [ ] Integrate CircleCI

### Story 3: REST API to create an article

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

## Day 2

### Story 4: Update an article

```
PATCH /api/articles/:slug-uuid
```

The body of the patch is shown below:

```json
{
    "title": "How to learn Spring Boot by building an app",
}
```

All the fields are optional.

The `slug` also gets updated when article is changed.

Returns the updated article.

```json
{
    "id": "a98fd991e69a",
    "slug": "how-to-learn-spring-boot-by-building-an-app",
    "title": "How to learn Spring Booot",
    "description": "Ever wonder how?",
    "body": "You have to believe",
    "tags": ["java", "spring-boot","tutorial"],
    "createdAt": "2019-11-24T03:22:56.637Z",
    "updatedAt": "2019-11-25T03:48:35.824Z",
    "favorited": false,
    "favoritesCount": 0,
}
```

### Story 5: Get an article

`GET /api/articles/:slug-uuid`

This returns single article

```json
{
    "id": "a98fd991e69a",
    "slug": "how-to-learn-spring-boot-by-building-an-app",
    "title": "How to learn Spring Booot",
    "description": "Ever wonder how?",
    "body": "You have to believe",
    "tags": ["java", "spring-boot","tutorial"],
    "createdAt": "2019-11-24T03:22:56.637Z",
    "updatedAt": "2019-11-25T03:48:35.824Z",
    "favorited": false,
    "favoritesCount": 0,
}
```

### Story 6: List articles

`GET /api/articles`

This should support pagination. 

### Story 7: Delete an article

```
DELETE /api/articles/:slug-uuid
```

## Day 3

### Story 8: Add comments to an article

```
POST /api/articles/:slug-uuid/comments
```

Example request body

```json
{
    "body": "Awesome tutorial!"
 }
```

This will return 201 if successfully created.

`body` is mandatory. Should return 400 if `body` is not present.

Application should check spam words in the body.  You can find list of English bad words from here - [Link](https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/edit#gid=0)

If request contains bad words then you should return HTTP status 400. 

You should also store IP address from which comment was made in the comment database table.

### Story 9: Get comments for an article

```
GET /api/articles/:slug-uuid/comments
```

This will return multiple comments

```json
[
    {
        "id": 1,
        "createdAt": "2019-11-18T03:22:56.637Z",
        "updatedAt": "2019-11-18T03:22:56.637Z",
        "body": "Awesome article!",
	      "idAddress": "10.101.0.1"
    },
    {
        "id": 2,
        "createdAt": "2019-11-18T03:22:56.637Z",
        "updatedAt": "2019-11-18T03:22:56.637Z",
        "body": "Bad article!",
         "idAddress": "10.101.0.3"
    }
]
```

### Story 10: Delete comment for an article

```
DELETE /api/articles/:slug-uuid/comments/:id
```

Returns 204 on successfully deletion.

Returns 404 if article or comment does not exist.

## Day 4
### Story 10 : An Article is having a STATUS
A newly created article is in `DRAFT` status. It can be modified to a `PUBLISHED` status.

`GET /api/articles/:slug-uuid`

This returns single article with STATUS

```json
{
    "id": "a98fd991e69a",
    "slug": "how-to-learn-spring-boot-by-building-an-app",
    "title": "How to learn Spring Booot",
    "description": "Ever wonder how?",
    "body": "You have to believe",
    "tags": ["java", "spring-boot","tutorial"],
    "createdAt": "2019-11-24T03:22:56.637Z",
    "updatedAt": "2019-11-25T03:48:35.824Z",
    "favorited": false,
    "favoritesCount": 0,
    "status" : "DRAFT"
}
```

### Story 11 : Get Articles By Status

`GET /api/articles?status=DRAFT`

This should support pagination. 

### Story 12 : Publish Article

Add `POST /api/articles/:slug-uuid/PUBLISH` to publish an article. This will changes the status to PUBLISH and send a mail to your xebia mail id. Mail sending can be configured using `JavaMailSender`. The article status should be changed even if the mail sending throws an error.

Response

1. It will return response code 204 if article is PUBLISHED successfully

2. It will return 400 if article is already PUBLISHED

3. It will return 500 if anything else happened

## Day 5

### Story 13: Find time to read for an article

You have to define a REST endpoint that will find time to read for an article given its id

```
GET /api/articles/:slug-uuid/timetoread
```

This should return following response

```json
{
"articleId": "slug-uuid",
"timeToRead": {
 "mins" : 3,
 "seconds" : 50
   } 
}
```

The logic to calculate time to read is `total number of words / speed of average human`

The speed of average human should be configurable.

## Day 6

### Story 14: Generate Tag based metrics 
You have to define a REST endpoint that will provide all tags, provided in all articles, with their occurance.

```
GET /api/articles/tags
```

This should return following response

```json
[{ "tag" : "java", "occurence" : "10"},
{ "tag" : "spring", "occurance" : "5"},
{ "tag" : "tutorial", "occurance" : "2"}
]
```

You need to look into the tags marked in an article. Tags metrics should not differentiate based on casing. 'Java','java', 'JAVA', 'JaVA' etc, should be considered one for determining count.

## Day 7

### Story 15:  Favourite an article

You have to design it yourself

Favourite count should change

### Story 16:  Unfavourite an article

You have to design it yourself

Favourite count should change

## Day 8

### Story 17: Only logged in users can create an article

You need to define mapping between user and article in the domain classes.

The article response need to be changed to below.

```json
{
    "id": "a98fd991e69a",
    "slug": "how-to-learn-spring-boot-by-building-an-app",
    "title": "How to learn Spring Booot",
    "description": "Ever wonder how?",
    "body": "You have to believe",
    "tags": ["java", "spring-boot","tutorial"],
    "createdAt": "2019-11-24T03:22:56.637Z",
    "updatedAt": "2019-11-25T03:48:35.824Z",
    "favorited": false,
    "favoritesCount": 0,
    "author": {
    "username":"shekhargulati",
    "fullName" : "Shekhar Gulati"
    }
}
```

## Day 9

## Story 18: User can only update articles that they created

In this story, you have to fix the update functionality to consider user

Create two articles one by user `u1` and another by user `u2`. If `u2` tries to update article created by `u1` then they should get an error response `403`.

### Story 19: User can only delete articles that they created

In this story, you have to fix the delete functionality to consider user.

Create two articles one by user `u1` and another by user `u2`. If `u2` tries to delete article created by `u1` then they should get an error response `403`.
