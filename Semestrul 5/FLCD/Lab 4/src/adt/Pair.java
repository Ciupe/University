package adt;

public class Pair<T1, T2>
{
    public T1 first;
    public T2 second;

    public Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return this.first.equals(pair.first) && this.second.equals(pair.second);
    }

    @Override
    public String toString()
    {
        return first.toString() + " " + second.toString();
    }
}