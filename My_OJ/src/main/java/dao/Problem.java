package dao;

public class Problem {
    private int id;
    private String title;
    private String level;
    private String description;
    private String codeTemplate;
    private String codeTest;

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                ", description='" + description + '\'' +
                ", codeTemplate='" + codeTemplate + '\'' +
                ", codeTest='" + codeTest + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    public String getCodeTest() {
        return codeTest;
    }

    public void setCodeTest(String codeTest) {
        this.codeTest = codeTest;
    }
}
