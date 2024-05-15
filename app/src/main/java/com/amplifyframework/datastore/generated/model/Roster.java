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

/** This is an auto generated class representing the Roster type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Rosters", type = Model.Type.USER, version = 1)
public final class Roster implements Model {
  public static final QueryField ID = field("Roster", "id");
  public static final QueryField COURSE = field("Roster", "courseRosterId");
  public static final QueryField STUDENT_NAME = field("Roster", "studentName");
  public static final QueryField STUDENT_ID = field("Roster", "studentID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Course") @BelongsTo(targetName = "courseRosterId", targetNames = {"courseRosterId"}, type = Course.class) Course course;
  private final @ModelField(targetType="String", isRequired = true) String studentName;
  private final @ModelField(targetType="String", isRequired = true) String studentID;
  private final @ModelField(targetType="Exams") @HasMany(associatedWith = "roster", type = Exams.class) List<Exams> exams = null;
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
  
  public Course getCourse() {
      return course;
  }
  
  public String getStudentName() {
      return studentName;
  }
  
  public String getStudentId() {
      return studentID;
  }
  
  public List<Exams> getExams() {
      return exams;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Roster(String id, Course course, String studentName, String studentID) {
    this.id = id;
    this.course = course;
    this.studentName = studentName;
    this.studentID = studentID;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Roster roster = (Roster) obj;
      return ObjectsCompat.equals(getId(), roster.getId()) &&
              ObjectsCompat.equals(getCourse(), roster.getCourse()) &&
              ObjectsCompat.equals(getStudentName(), roster.getStudentName()) &&
              ObjectsCompat.equals(getStudentId(), roster.getStudentId()) &&
              ObjectsCompat.equals(getCreatedAt(), roster.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), roster.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCourse())
      .append(getStudentName())
      .append(getStudentId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Roster {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("course=" + String.valueOf(getCourse()) + ", ")
      .append("studentName=" + String.valueOf(getStudentName()) + ", ")
      .append("studentID=" + String.valueOf(getStudentId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static StudentNameStep builder() {
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
  public static Roster justId(String id) {
    return new Roster(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      course,
      studentName,
      studentID);
  }
  public interface StudentNameStep {
    StudentIdStep studentName(String studentName);
  }
  

  public interface StudentIdStep {
    BuildStep studentId(String studentId);
  }
  

  public interface BuildStep {
    Roster build();
    BuildStep id(String id);
    BuildStep course(Course course);
  }
  

  public static class Builder implements StudentNameStep, StudentIdStep, BuildStep {
    private String id;
    private String studentName;
    private String studentID;
    private Course course;
    public Builder() {
      
    }
    
    private Builder(String id, Course course, String studentName, String studentID) {
      this.id = id;
      this.course = course;
      this.studentName = studentName;
      this.studentID = studentID;
    }
    
    @Override
     public Roster build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Roster(
          id,
          course,
          studentName,
          studentID);
    }
    
    @Override
     public StudentIdStep studentName(String studentName) {
        Objects.requireNonNull(studentName);
        this.studentName = studentName;
        return this;
    }
    
    @Override
     public BuildStep studentId(String studentId) {
        Objects.requireNonNull(studentId);
        this.studentID = studentId;
        return this;
    }
    
    @Override
     public BuildStep course(Course course) {
        this.course = course;
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
    private CopyOfBuilder(String id, Course course, String studentName, String studentId) {
      super(id, course, studentName, studentID);
      Objects.requireNonNull(studentName);
      Objects.requireNonNull(studentID);
    }
    
    @Override
     public CopyOfBuilder studentName(String studentName) {
      return (CopyOfBuilder) super.studentName(studentName);
    }
    
    @Override
     public CopyOfBuilder studentId(String studentId) {
      return (CopyOfBuilder) super.studentId(studentId);
    }
    
    @Override
     public CopyOfBuilder course(Course course) {
      return (CopyOfBuilder) super.course(course);
    }
  }
  

  public static class RosterIdentifier extends ModelIdentifier<Roster> {
    private static final long serialVersionUID = 1L;
    public RosterIdentifier(String id) {
      super(id);
    }
  }
  
}
