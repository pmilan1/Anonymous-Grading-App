package com.amplifyframework.datastore.generated.model;

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

/** This is an auto generated class representing the Instructor type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Instructors", type = Model.Type.USER, version = 1)
public final class Instructor implements Model {
  public static final QueryField ID = field("Instructor", "id");
  public static final QueryField USERNAME = field("Instructor", "username");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="Course") @HasMany(associatedWith = "instructor", type = Course.class) List<Course> courses = null;
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
  
  public String getUsername() {
      return username;
  }
  
  public List<Course> getCourses() {
      return courses;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Instructor(String id, String username) {
    this.id = id;
    this.username = username;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Instructor instructor = (Instructor) obj;
      return ObjectsCompat.equals(getId(), instructor.getId()) &&
              ObjectsCompat.equals(getUsername(), instructor.getUsername()) &&
              ObjectsCompat.equals(getCreatedAt(), instructor.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), instructor.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Instructor {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UsernameStep builder() {
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
  public static Instructor justId(String id) {
    return new Instructor(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      username);
  }
  public interface UsernameStep {
    BuildStep username(String username);
  }
  

  public interface BuildStep {
    Instructor build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UsernameStep, BuildStep {
    private String id;
    private String username;
    public Builder() {
      
    }
    
    private Builder(String id, String username) {
      this.id = id;
      this.username = username;
    }
    
    @Override
     public Instructor build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Instructor(
          id,
          username);
    }
    
    @Override
     public BuildStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
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
    private CopyOfBuilder(String id, String username) {
      super(id, username);
      Objects.requireNonNull(username);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
  }
  

  public static class InstructorIdentifier extends ModelIdentifier<Instructor> {
    private static final long serialVersionUID = 1L;
    public InstructorIdentifier(String id) {
      super(id);
    }
  }
  
}
