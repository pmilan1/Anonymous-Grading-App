package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Course type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Courses", type = Model.Type.USER, version = 1)
public final class Course implements Model {
  public static final QueryField ID = field("Course", "id");
  public static final QueryField COURSENAME = field("Course", "coursename");
  public static final QueryField INSTRUCTOR = field("Course", "instructorCoursesId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String coursename;
  private final @ModelField(targetType="Instructor") @BelongsTo(targetName = "instructorCoursesId", targetNames = {"instructorCoursesId"}, type = Instructor.class) Instructor instructor;
  private final @ModelField(targetType="Roster") @HasMany(associatedWith = "course", type = Roster.class) List<Roster> roster = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getCoursename() {
      return coursename;
  }
  
  public Instructor getInstructor() {
      return instructor;
  }
  
  public List<Roster> getRoster() {
      return roster;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Course(String id, String coursename, Instructor instructor) {
    this.id = id;
    this.coursename = coursename;
    this.instructor = instructor;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Course course = (Course) obj;
      return ObjectsCompat.equals(getId(), course.getId()) &&
              ObjectsCompat.equals(getCoursename(), course.getCoursename()) &&
              ObjectsCompat.equals(getInstructor(), course.getInstructor()) &&
              ObjectsCompat.equals(getCreatedAt(), course.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), course.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCoursename())
      .append(getInstructor())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Course {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("coursename=" + String.valueOf(getCoursename()) + ", ")
      .append("instructor=" + String.valueOf(getInstructor()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static CoursenameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Course justId(String id) {
    return new Course(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      coursename,
      instructor);
  }
  public interface CoursenameStep {
    BuildStep coursename(String coursename);
  }
  

  public interface BuildStep {
    Course build();
    BuildStep id(String id);
    BuildStep instructor(Instructor instructor);
  }
  

  public static class Builder implements CoursenameStep, BuildStep {
    private String id;
    private String coursename;
    private Instructor instructor;
    public Builder() {
      
    }
    
    private Builder(String id, String coursename, Instructor instructor) {
      this.id = id;
      this.coursename = coursename;
      this.instructor = instructor;
    }
    
    @Override
     public Course build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Course(
          id,
          coursename,
          instructor);
    }
    
    @Override
     public BuildStep coursename(String coursename) {
        Objects.requireNonNull(coursename);
        this.coursename = coursename;
        return this;
    }
    
    @Override
     public BuildStep instructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String coursename, Instructor instructor) {
      super(id, coursename, instructor);
      Objects.requireNonNull(coursename);
    }
    
    @Override
     public CopyOfBuilder coursename(String coursename) {
      return (CopyOfBuilder) super.coursename(coursename);
    }
    
    @Override
     public CopyOfBuilder instructor(Instructor instructor) {
      return (CopyOfBuilder) super.instructor(instructor);
    }
  }
  

  public static class CourseIdentifier extends ModelIdentifier<Course> {
    private static final long serialVersionUID = 1L;
    public CourseIdentifier(String id) {
      super(id);
    }
  }
  
}
