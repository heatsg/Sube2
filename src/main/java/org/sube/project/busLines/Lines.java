package org.sube.project.busLines;

public enum Lines {
    LINE_501("501"),
    LINE_511("511"),
    LINE_512("512"),
    LINE_522("522"),
    LINE_523("523"),
    LINE_525("525"),
    LINE_531("531"),
    LINE_532("532"),
    LINE_533("533"),
    LINE_541("541"),
    LINE_542("542"),
    LINE_543("543"),
    LINE_551("551"),
    LINE_552("552"),
    LINE_553("553"),
    LINE_554("554"),
    LINE_555("555"),
    LINE_562("562"),
    LINE_563("563"),
    LINE_571("571"),
    LINE_573("573"),
    LINE_581("581"),
    LINE_591("591"),
    LINE_593("593");

    private final String lineNumber;

    Lines(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return lineNumber;
    }

}
