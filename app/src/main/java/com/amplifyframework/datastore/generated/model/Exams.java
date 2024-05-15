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

/** This is an auto generated class representing the Exams type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Exams", type = Model.Type.USER, version = 1)
public final class Exams implements Model {
  public static final QueryField ID = field("Exams", "id");
  public static final QueryField ROSTER = field("Exams", "rosterExamsId");
  public static final QueryField BARCODE = field("Exams", "barcode");
  public static final QueryField EXAM_NAME = field("Exams", "examName");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Roster") @BelongsTo(targetName = "rosterExamsId", targetNames = {"rosterExamsId"}, type = Roster.class) Roster roster;
  private final @ModelField(targetType="Int") Integer barcode;
  private final @ModelField(targetType="String", isRequired = true) String examName;
  private final @ModelField(targetType="Grade") @HasMany(associatedWith = "exams", type = Grade.class) List<Grade> grade = null;
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
  
  public Roster getRoster() {
      return roster;
  }
  
  public Integer getBarcode() {
      return barcode;
  }
  
  public String getExamName() {
      return examName;
  }
  
  public List<Grade> getGrade() {
      return grade;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Exams(String id, Roster roster, Integer barcode, String examName) {
    this.id = id;
    this.roster = roster;
    this.barcode = barcode;
    this.examName = examName;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Exams exams = (Exams) obj;
      return ObjectsCompat.equals(getId(), exams.getId()) &&
              ObjectsCompat.equals(getRoster(), exams.getRoster()) &&
              ObjectsCompat.equals(getBarcode(), exams.getBarcode()) &&
              ObjectsCompat.equals(getExamName(), exams.getExamName()) &&
              ObjectsCompat.equals(getCreatedAt(), exams.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), exams.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRoster())
      .append(getBarcode())
      .append(getExamName())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Exams {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("roster=" + String.valueOf(getRoster()) + ", ")
      .append("barcode=" + String.valueOf(getBarcode()) + ", ")
      .append("examName=" + String.valueOf(getExamName()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ExamNameStep builder() {
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
  public static Exams justId(String id) {
    return new Exams(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      roster,
      barcode,
      examName);
  }
  public interface ExamNameStep {
    BuildStep examName(String examName);
  }
  

  public interface BuildStep {
    Exams build();
    BuildStep id(String id);
    BuildStep roster(Roster roster);
    BuildStep barcode(Integer barcode);
  }
  

  public static class Builder implements ExamNameStep, BuildStep {
    private String id;
    private String examName;
    private Roster roster;
    private Integer barcode;
    public Builder() {
      
    }
    
    private Builder(String id, Roster roster, Integer barcode, String examName) {
      this.id = id;
      this.roster = roster;
      this.barcode = barcode;
      this.examName = examName;
    }
    
    @Override
     public Exams build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Exams(
          id,
          roster,
          barcode,
          examName);
    }
    
    @Override
     public BuildStep examName(String examName) {
        Objects.requireNonNull(examName);
        this.examName = examName;
        return this;
    }
    
    @Override
     public BuildStep roster(Roster roster) {
        this.roster = roster;
        return this;
    }
    
    @Override
     public BuildStep barcode(Integer barcode) {
        this.barcode = barcode;
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
    private CopyOfBuilder(String id, Roster roster, Integer barcode, String examName) {
      super(id, roster, barcode, examName);
      Objects.requireNonNull(examName);
    }
    
    @Override
     public CopyOfBuilder examName(String examName) {
      return (CopyOfBuilder) super.examName(examName);
    }
    
    @Override
     public CopyOfBuilder roster(Roster roster) {
      return (CopyOfBuilder) super.roster(roster);
    }
    
    @Override
     public CopyOfBuilder barcode(Integer barcode) {
      return (CopyOfBuilder) super.barcode(barcode);
    }
  }
  

  public static class ExamsIdentifier extends ModelIdentifier<Exams> {
    private static final long serialVersionUID = 1L;
    public ExamsIdentifier(String id) {
      super(id);
    }
  }
  
}
