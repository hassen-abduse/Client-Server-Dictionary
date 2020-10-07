/******************************************************************
 * Word Class the blue print for the words to be                  *
 * added, removed and searched for. from the dictionary server    *
 * by the dictionary clients.                                     *
 ******************************************************************/
public class Word {
    private String word;
    private String definition1;
    private String definition2;
    private String wordClass;

    /*******************************************************************************
     *  Constructor for the word class                                              *
     * @param word word string                                                      *
     * @param wordClass the part of speech where the word belongs to                *
     * @param definition1 the definition of the word                                *
     * @param definition2 the other definition of the word if it has                *
     *******************************************************************************/
    public Word(String word, String wordClass, String definition1, String definition2) {
        this.word = word;
        this.wordClass = wordClass;
        this.definition1 = definition1;
        this.definition2 = definition2;


    }

    /****************************************************************
     *  Constructor for words which have one definition
     * @param word word string
     * @param wordClass the part of speech where the word belongs to
     * @param definition1 the difinition of the word
     */
    public Word(String word, String wordClass, String definition1) {

        this.word = word;
        this.wordClass = wordClass;
        this.definition1 = definition1;
        this.definition2 = "";
    }

    /*
    returns the word string
     */
    public String getWord() {
        return word;
    }

    /*
        returns the first definition of a word
     */
    public String getDefinition1() {
        return definition1;
    }

    /*
        returns the second definition of the word
     */
    public String getDefinition2() {
        return definition2;
    }

    /*
        returns the part of speech where the word belongs to
     */
    public String getWordClass() {
        return wordClass;
    }
}

