package models;

public class Movie {

    private int Id;
    private String Title;
    private String Director;
    private String Genre;
    private String Description;
    private String Year;
    private String Rating;
    private int Duration_min;
    private String PosterLink;


    public String getTitle() {
        return Title;
    }

    public String getDirector() {
        return Director;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDescription() {
        return Description;
    }

    public String getYear() {
        return Year;
    }

    public String getRating() {
        return Rating;
    }

    public int getDuration_min() {
        return Duration_min;
    }

    public String getPosterLink() {
        return PosterLink;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setYear(String year) {
        Year = year;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public void setDuration_min(int duration_min) {
        Duration_min = duration_min;
    }

    public void setPosterLink(String posterLink) {
        this.PosterLink = posterLink;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String toString() {
        return Id + "&" + Title + "&" + Director + "&" + Genre + "&" + Description + "&" + Year + "&" + Rating + "&" + Duration_min + "&" + PosterLink;
    }



}
