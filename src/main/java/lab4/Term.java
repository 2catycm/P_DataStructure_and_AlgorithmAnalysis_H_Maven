package lab4;

class Term implements Comparable<Term> {
    private final int coefficient;
    private final int exponent;

    public Term(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public int getExponent() {
        return exponent;
    }

    public boolean hasSameExponent(Term other) {
        return this.exponent == other.exponent;
    }

    public Term add(Term other) {
        if (this.exponent != other.exponent)
            throw new UnsupportedOperationException("指数不同！");
        return new Term(this.coefficient + other.coefficient, this.exponent);
    }

    @Override
    public String toString() {
        return coefficient + " " + exponent;
    }

    @Override
    public int compareTo(Term other) {
        return Integer.compare(this.exponent, other.exponent);
    }
}
