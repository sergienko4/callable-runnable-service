public enum TaskType {
    IO(1),
    COMPUTATIONAL(2),
    UNKNOWN(3);
    private int priority;

    private TaskType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("task type is: ");
        switch (this.priority) {
            case 1:
                sb.append("IO");
                break;
            case 2:
                sb.append("COMPUTATIONAL");
                break;
            case 3:
                sb.append("UNKNOWN");
                break;
            default:
                throw new IllegalArgumentException("The enum: " + this.getClass().getName() + " with value: " + this.priority + " is wrong!");
        }
        return sb.toString();
    }
}
