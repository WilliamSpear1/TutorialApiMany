package com.Tutorial.Comments.Tutorial.Exception;

import java.util.Date;

public class ErrorMessage {
    // members /////////////////////////////////////////////////////////////////////////////////
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
    
    // methods /////////////////////////////////////////////////////////////////////////////////
      public ErrorMessage(
        int statusCode,
        Date timestamp,
        String message,
        String description) {
          this.statusCode  = statusCode;
          this.timestamp   = timestamp;
          this.message     = message;
          this.description = description;
      }
    
      // getters and setters ///////////////////////////////////////////////////////////////////
      public int getStatusCode() { return statusCode; }
      public Date getTimestamp() {return timestamp; }
      public String getMessage() {return message; }
      public String getDescription() { return description;}
     
}
