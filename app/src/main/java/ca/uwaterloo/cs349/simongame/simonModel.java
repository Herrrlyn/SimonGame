package ca.uwaterloo.cs349.simongame;


import android.util.Log;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

/**
 * simonModel
 *
 * Created by Haowen Yin on 2017-12-03.
 */

class simonModel extends Observable
{
    // Create static instance of this mModel
    private static final simonModel ourInstance = new simonModel();
    static simonModel getInstance()
    {
        return ourInstance;
    }

    public enum State { START, COMPUTER, HUMAN, LOSE, WIN };
    // Private Variables
    private State state;
    private int score, length, buttons, index, difficulty;
    private boolean debug = true;
    private Vector<Integer> sequence;
    //private int difficulty;



    /**
     * Model Constructor:
     * - Init member variables
     */
    public simonModel(){
        //debug = _debug;
        length = 1;
        //buttons = _buttons;
        state = State.START;
        sequence = new Vector<>();
        score = 0;
        difficulty = 0;


    }
    /*public void clearModel() {

    }*/
    public int getScore() { return score; }
    public void setScore(int s) { score = s; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int d) { difficulty = d; }

    public int getLength() { return length; }
    public void setLength(int l) { length = l; }

    public int getNumButtons() { return buttons; }
    public void setNumButtons(int b) { buttons = b; }

    public int getIndex() { return index; }
    public void setIndex(int i) { index = i; }

    public boolean getDebug() { return debug;}
    /*public void setDebug(boolean d) {
        debug = d;
    }*/

    public State getState() {
        return state;
    }

    public void setState(State s){
        state = s;
    }

    public String getStateAsString() {
        String retval = null;
        switch (getState()) {
            case START:
                retval = "START";
            break;

            case COMPUTER:
                retval = "COMPUTER";
            break;
            case HUMAN:
                retval = "HUMAN";
            break;

            case LOSE:
                retval = "LOSE";
            break;

            case WIN:
                retval = "WIN";
            break;
        }


        return retval;
    }

    public void newRound() {
        if (debug) {
            Log.d("DEMO", "[DEBUG] newRound, Simon::state " + getStateAsString());
        }

        if (getState() == State.LOSE) {
            if (debug) {
                Log.d("DEMO", "[DEBUG] reset length and score after loss");
            }
            setLength(1);
            setScore(0);
        }

        sequence.clear();

        if (debug) { Log.d("DEMO", "[DEBUG] new sequence: "); }

        for (int i = 0; i < length; i++){
            Random rand = new Random();
            Integer b = rand.nextInt(getNumButtons());
            sequence.add(i, b);
            if (debug) {
                Log.d("DEMO", i+": "+b + " ");
            }
        }
        setIndex(0);
        setState(State.COMPUTER);
    }

    public Integer nextButton() {
        if (getState() != State.COMPUTER){
            Log.d("DEMO", "[WARNING] nextButton called in " + getStateAsString());
            return -1;
        }

        Integer button = sequence.get(getIndex());

        if (debug) {
            Log.d("DEMO", "[DEBUG] nextButton:  index " + index + " button " + button);

        }

        setIndex(getIndex()+1);
        if(getIndex() >=sequence.size()) {
            setIndex(0);
            state = State.HUMAN;
        }
        return button;
    }

    public boolean verifyButton(Integer button){
        if (getState() != State.HUMAN) {
            Log.d("DEMO", "[WARNING] verifyButton: called in " + getStateAsString());
            return false;
        } else if (getState() == State.HUMAN) {
            Log.d("DEMO", "got right" + getStateAsString());
        }
        boolean correct = (button == sequence.get(index));

        if (debug) {
            Log.d("DEMO", "[DEBUG] verifyButton: index " + getIndex()
                    + ", pushed " + button + ", sequence " + sequence.get(index));
        }

        index++;

        if (!correct) {
            setState(State.LOSE);
            if (debug) {
                Log.d("DEMO", ", wrong. ");
                Log.d("DEMO", "[DEBUG] state is now " + getStateAsString());

            }
        } else {
            if (debug) { Log.d("DEMO", ", correct."); }

            if (getIndex() == sequence.size()) {
                setState(State.WIN);
                setScore(getScore()+1);
                setLength(getLength()+1);

                if (debug) {
                    Log.d("DEMO", "[DEBUG] state is now " + getStateAsString());
                    Log.d("DEMO", "[DEBUG] new score " + getScore() + ", length increased to " + getLength());
                }
            }
        }
        return correct;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Observable Methods
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Helper method to make it easier to initialize all observers
     */
    public void initObservers()
    {
        setChanged();
        notifyObservers();
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     *
     * @param o the observer to be deleted.
     */
    @Override
    public synchronized void deleteObserver(Observer o)
    {
        super.deleteObserver(o);
    }

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param o an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    @Override
    public synchronized void addObserver(Observer o)
    {
        super.addObserver(o);
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Override
    public synchronized void deleteObservers()
    {
        super.deleteObservers();
    }

    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see Observable#clearChanged()
     * @see Observable#hasChanged()
     * @see Observer#update(Observable, Object)
     */
    @Override
    public void notifyObservers()
    {
        super.notifyObservers();
    }
}
