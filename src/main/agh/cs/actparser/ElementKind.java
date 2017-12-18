package agh.cs.actparser;

public enum ElementKind {
    Document,
    Section, // Dział
    //Chapter, // Rozdział
    Article, // Artykuł
    Paragraph, // Ustęp
    Point, // Punkt
    Letter, // litera
    Indent, // Tiret
    Plaintext;

    public int toLevel(){
        return this.compareTo(Document);
    }

}
