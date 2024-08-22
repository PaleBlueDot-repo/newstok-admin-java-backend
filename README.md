

# Admin_Server to User_Server Api call
### API Documentation: `GET /user/getDashboard`

This document describes the endpoint `GET /user/getDashboard`, which the Admin server can call to retrieve a JSON response from the User server. The data returned provides an overview of the user's interaction with news reels.

#### Endpoint
- **URL**: `http://localhost:8081/user/getDashboard`
- **Method**: `GET`
- **Response Format**: JSON

#### Response JSON Structure
The JSON response from the User server will include details about the reels that the user has interacted with, as well as aggregated statistics across all reels. All data fields will be returned as strings.

```json
{
    "reelsList": [
        {
            "reelsId": "23",
            "views": "20",
            "likes": "10",
            "status": "1"
        }
    ],
    "published": "70",
    "watchtime": "320",
    "likes": "150",
    "newsReel_Views": "60.5k"
}
```

#### Response Field Descriptions

- **`reelsList`**: An array of objects, where each object represents a reel that the user has interacted with at least once.
    - **`reelsId`**: A unique identifier for the reel.
    - **`views`**: The total number of views this reel has received across all users of the app.
    - **`likes`**: The total number of likes this reel has received across all users of the app.
    - **`status`**: Indicates the current status of the reel.
        - **`status: "1"`**: The reel is uploaded on the user's feed.

- **`published`**: The total number of reels that exist on the user's side.

- **`watchtime`**: The total watch time (in minutes) accumulated across all users for all reels.

- **`likes`**: The total number of likes accumulated across all users for all reels.

- **`newsReel_Views`**: The total number of views accumulated across all users for all reels, represented as a string (e.g., `"60.5k"` for 60,500 views).


# User_Server to Admin_Server Api call

### API Documentation: Reels Recommendation System

This document describes the communication between the User server and Admin server for the Reels Recommendation System. The User server sends a POST request to the Admin server, which returns a list of recommended reels based on the user's interactions.

- **Endpoint**: `http://localhost:8080/admin/getReelsRecommendation`
- **Method**: `POST`
- **Request Body**: JSON

##### Request JSON Structure
The User server sends a JSON object with the user's interaction data and interests to the Admin server. The interactions include a list of reels that the user has interacted with, along with a score for each interaction.

```json
{
    "user_id": 1,
    "interactions": [
        {"user_id": 1, "reels_id": 1, "score": 5.0},
        {"user_id": 1, "reels_id": 2, "score": 3.0},
        {"user_id": 1, "reels_id": 3, "score": 4.0},
        {"user_id": 2, "reels_id": 1, "score": 2.0},
        {"user_id": 2, "reels_id": 3, "score": 5.0},
        {"user_id": 3, "reels_id": 2, "score": 4.0},
        {"user_id": 3, "reels_id": 4, "score": 5.0}
    ],
    "interest": "city,bd"
}
```
### Interest Domain{

- bangla_newspaper = bangladesh,world,sports,science-technology,lifestyle,exception
-  english_newspaper = business-economy,city,front-page,back-page,entertainment,national,sports

}
##### Request Field Descriptions
- **`user_id`**: The ID of the user for whom the recommendation is being requested.
- **`interactions`**: A list of interactions where each object contains:
    - **`user_id`**: The ID of the user who interacted with the reel.
    - **`reels_id`**: The ID of the reel.
    - **`score`**: A score representing the user's interaction with the reel.
- **`interest`**: A comma-separated string indicating the user's interests.

---

#### 2. Admin Server Response: Reels Recommendation

- **Response Format**: JSON (List of Objects)

##### Response JSON Structure
The Admin server responds with a list of reels that are recommended for the user. Each reel object includes necessary information for display, including the reel ID, news ID, styling details, and a Base64-encoded image.

```json
[
    {
        "news": {
            "id": 101,
            "newspaperName": "The Sports Daily",
            "category": "sports",
            "title": "Exciting Sports Event",
            "article": "This is an article about the latest sports event.",
            "link": "http://example.com/sports-article",
            "published": "2024-08-20"
        },
        "reels": {
            "reelsId": 1,
            "newsId": 101,
            "background_color": "#FFFFFF",
            "font_color": "#000000",
            "font_family": "Arial, sans-serif",
            "image": "",
            "music": "data:audio/mp3;base64,//uQxAAA...",
            "summary": "This is a summary of the news reel content. It provides a brief overview of the key points covered in the reel.",
            "title": "Breaking News: Major Event"
        }
    },
    {
        "news": {
            "id": 103,
            "newspaperName": "Bangladesh Times",
            "category": "bangladesh",
            "title": "Political Landscape in Bangladesh",
            "article": "A deep dive into Bangladesh’s political situation.",
            "link": "http://example.com/bangladesh-article",
            "published": "2024-08-18"
        },
        "reels": {
            "reelsId": 23,
            "newsId": 103,
            "background_color": "#CFE2F3",
            "font_color": "#4A4A4A",
            "font_family": "Verdana, sans-serif",
            "image": "",
            "music": "",
            "summary": "An insightful analysis of the recent changes in the industry, highlighting key trends and predictions for the future.",
            "title": "Industry Trends and Future Predictions"
        }
    }
]
```



---

### Notes
- **Image Handling**: The `image` field is Base64-encoded. To display the image, decode this string on the frontend or in the application where the reel information is rendered.
- **Data Format**: All string fields, including numbers and IDs, are provided in string format.

