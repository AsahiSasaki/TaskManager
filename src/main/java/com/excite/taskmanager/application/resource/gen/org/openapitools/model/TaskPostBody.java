package com.excite.taskmanager.application.resource.gen.org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * TaskPostBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-15T10:10:31.462474+09:00[Asia/Tokyo]")
public class TaskPostBody {

  private String title;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate deadline;

  public TaskPostBody title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @Size(min = 1, max = 20) 
  @Schema(name = "title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public TaskPostBody description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @Pattern(regexp = "^([^\\x01-\\x7E])+") @Size(max = 50) 
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TaskPostBody deadline(LocalDate deadline) {
    this.deadline = deadline;
    return this;
  }

  /**
   * 本日以降の日付をYYYY-MM-DD形式で指定してください。
   * @return deadline
  */
  @Valid 
  @Schema(name = "deadline", description = "本日以降の日付をYYYY-MM-DD形式で指定してください。", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deadline")
  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskPostBody taskPostBody = (TaskPostBody) o;
    return Objects.equals(this.title, taskPostBody.title) &&
        Objects.equals(this.description, taskPostBody.description) &&
        Objects.equals(this.deadline, taskPostBody.deadline);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, deadline);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaskPostBody {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    deadline: ").append(toIndentedString(deadline)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

