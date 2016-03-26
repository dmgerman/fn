begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_comment
comment|/**  * This is an immutable class representing information of either<CODE>author</CODE>  * or<CODE>editor</CODE> field in bibtex record.  *<p>  * Constructor performs parsing of raw field text and stores preformatted data.  * Various accessor methods return author/editor field in different formats.  *<p>  * Parsing algorithm is designed to satisfy two requirements: (a) when author's  * name is typed correctly, the result should coincide with the one of BiBTeX;  * (b) for erroneous names, output should be reasonable (but may differ from  * BiBTeX output). The following rules are used:  *<ol>  *<li> 'author field' is a sequence of tokens;  *<ul>  *<li> tokens are separated by sequences of whitespaces (<CODE>Character.isWhitespace(c)==true</CODE>),  * commas (,), dashes (-), and tildas (~);  *<li> every comma separates tokens, while sequences of other separators are  * equivalent to a single separator; for example: "a - b" consists of 2 tokens  * ("a" and "b"), while "a,-,b" consists of 3 tokens ("a", "", and "b")  *<li> anything enclosed in braces belonges to a single token; for example:  * "abc x{a,b,-~ c}x" consists of 2 tokens, while "abc xa,b,-~ cx" consists of 4  * tokens ("abc", "xa","b", and "cx");  *<li> a token followed immediately by a dash is "dash-terminated" token, and  * all other tokens are "space-terminated" tokens; for example: in "a-b- c - d"  * tokens "a" and "b" are dash-terminated and "c" and "d" are space-terminated;  *<li> for the purposes of splitting of 'author name' into parts and  * construction of abbreviation of first name, one needs definitions of first  * latter of a token, case of a token, and abbreviation of a token:  *<ul>  *<li> 'first letter' of a token is the first letter character (<CODE>Character.isLetter(c)==true</CODE>)  * that does not belong to a sequence of letters that immediately follows "\"  * character, with one exception: if "\" is followed by "aa", "AA", "ae", "AE",  * "l", "L", "o", "O", "oe", "OE", "i", or "j" followed by non-letter, the  * 'first letter' of a token is a letter that follows "\"; for example: in  * "a{x}b" 'first letter' is "a", in "{\"{U}}bel" 'first letter' is "U", in  * "{\noopsort{\"o}}xyz" 'first letter' is "o", in "{\AE}x" 'first letter' is  * "A", in "\aex\ijk\Oe\j" 'first letter' is "j"; if there is no letter  * satisfying the above rule, 'first letter' is undefined;  *<li> token is "lower-case" token, if its first letter id defined and is  * lower-case (<CODE>Character.isLowerCase(c)==true</CODE>), and token is  * "upper-case" token otherwise;  *<li> 'abbreviation' of a token is the shortest prefix of the token that (a)  * contains 'first letter' and (b) is braces-balanced; if 'first letter' is  * undefined, 'abbreviation' is the token itself; in the above examples,  * 'abbreviation's are "a", "{\"{U}}", "{\noopsort{\"o}}", "{\AE}",  * "\aex\ijk\Oe\j";  *</ul>  *<li> the behavior based on the above definitions will be erroneous only in  * one case: if the first-name-token is "{\noopsort{A}}john", we abbreviate it  * as "{\noopsort{A}}.", while BiBTeX produces "j."; fixing this problem,  * however, requires processing of the preabmle;  *</ul>  *<li> 'author name's in 'author field' are subsequences of tokens separated by  * token "and" ("and" is case-insensitive); if 'author name' is an empty  * sequence of tokens, it is ignored; for examle, both "John Smith and Peter  * Black" and "and and John Smith and and Peter Black" consists of 2 'author  * name's "Johm Smith" and "Peter Black" (in erroneous situations, this is a bit  * different from BiBTeX behavior);  *<li> 'author name' consists of 'first-part', 'von-part', 'last-part', and  * 'junior-part', each of which is a sequence of tokens; how a sequence of  * tokens has to be splitted into these parts, depends the number of commas:  *<ul>  *<li> no commas, all tokens are upper-case: 'junior-part' and 'von-part' are  * empty, 'last-part' consist of the last token, 'first-part' consists of all  * other tokens ('first-part' is empty, if 'author name' consists of a single  * token); for example, in "John James Smith", 'last-part'="Smith" and  * 'first-part'="John James";  *<li> no commas, there exists lower-case token: 'junior-part' is empty,  * 'first-part' consists of all upper-case tokens before the first lower-case  * token, 'von-part' consists of lower-case tokens starting the first lower-case  * token and ending the lower-case token that is followed by upper-case token,  * 'last-part' consists of the rest of tokens; note that both 'first-part' and  * 'latst-part' may be empty and 'last-part' may contain lower-case tokens; for  * example: in "von der", 'first-part'='last-part'="", 'von-part'="von der"; in  * "Charles Louis Xavier Joseph de la Vall{\'e}e la Poussin",  * 'first-part'="Charles Louis Xavier Joseph", 'von-part'="de la",  * 'last-part'="Vall{\'e}e la Poussin";  *<li> one comma: 'junior-part' is empty, 'first-part' consists of all tokens  * after comma, 'von-part' consists of the longest sequence of lower-case tokens  * in the very beginning, 'last-part' consists of all tokens after 'von-part'  * and before comma; note that any part can be empty; for example: in "de la  * Vall{\'e}e la Poussin, Charles Louis Xavier Joseph", 'first-part'="Charles  * Louis Xavier Joseph", 'von-part'="de la", 'last-part'="Vall{\'e}e la  * Poussin"; in "Joseph de la Vall{\'e}e la Poussin, Charles Louis Xavier",  * 'first-part'="Charles Louis Xavier", 'von-part'="", 'last-part'="Joseph de la  * Vall{\'e}e la Poussin";  *<li> two or more commas (any comma after the second one is ignored; it merely  * separates tokens): 'junior-part' consists of all tokens between first and  * second commas, 'first-part' consists of all tokens after the second comma,  * tokens before the first comma are splitted into 'von-part' and 'last-part'  * similarly to the case of one comma; for example: in "de la Vall{\'e}e  * Poussin, Jr., Charles Louis Xavier Joseph", 'first-part'="Charles Louis  * Xavier Joseph", 'von-part'="de la", 'last-part'="Vall{\'e}e la Poussin", and  * 'junior-part'="Jr.";  *</ul>  *<li> when 'first-part', 'last-part', 'von-part', or 'junior-part' is  * reconstructed from tokens, tokens in a part are separated either by space or  * by dash, depending on whether the token before the separator was  * space-terminated or dash-terminated; for the last token in a part it does not  * matter whether it was dash- or space-terminated;  *<li> when 'first-part' is abbreviated, each token is replaced by its  * abbreviation followed by a period; separators are the same as in the case of  * non-abbreviated name; for example: in "Heinrich-{\"{U}}bel Kurt von Minich",  * 'first-part'="Heinrich-{\"{U}}bel Kurt", and its abbreviation is "H.-{\"{U}}.  * K."  *</ol>  */
end_comment

begin_class
DECL|class|AuthorList
specifier|public
class|class
name|AuthorList
block|{
DECL|field|authors
specifier|private
specifier|final
name|List
argument_list|<
name|Author
argument_list|>
name|authors
decl_stmt|;
comment|// Variables for storing computed strings, so they only need to be created once:
DECL|field|authorsNatbib
specifier|private
name|String
name|authorsNatbib
decl_stmt|;
DECL|field|authorsFirstFirstAnds
specifier|private
name|String
name|authorsFirstFirstAnds
decl_stmt|;
DECL|field|authorsAlph
specifier|private
name|String
name|authorsAlph
decl_stmt|;
DECL|field|authorsFirstFirst
specifier|private
specifier|final
name|String
index|[]
name|authorsFirstFirst
init|=
operator|new
name|String
index|[
literal|4
index|]
decl_stmt|;
DECL|field|authorsLastOnly
specifier|private
specifier|final
name|String
index|[]
name|authorsLastOnly
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
DECL|field|authorLastFirstAnds
specifier|private
specifier|final
name|String
index|[]
name|authorLastFirstAnds
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
DECL|field|authorsLastFirst
specifier|private
specifier|final
name|String
index|[]
name|authorsLastFirst
init|=
operator|new
name|String
index|[
literal|4
index|]
decl_stmt|;
DECL|field|authorsLastFirstFirstLast
specifier|private
specifier|final
name|String
index|[]
name|authorsLastFirstFirstLast
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
DECL|field|AUTHOR_CACHE
specifier|private
specifier|static
specifier|final
name|WeakHashMap
argument_list|<
name|String
argument_list|,
name|AuthorList
argument_list|>
name|AUTHOR_CACHE
init|=
operator|new
name|WeakHashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * Creates a new list of authors.      *<p>      * Don't call this constructor directly but rather use the getAuthorList()      * method which caches its results.      *      * @param authors the list of authors which should underlie this instance      */
DECL|method|AuthorList (List<Author> authors)
specifier|protected
name|AuthorList
parameter_list|(
name|List
argument_list|<
name|Author
argument_list|>
name|authors
parameter_list|)
block|{
name|this
operator|.
name|authors
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|authors
argument_list|)
expr_stmt|;
block|}
comment|/**      * Retrieve an AuthorList for the given string of authors or editors.      *<p>      * This function tries to cache the parsed AuthorLists by the string passed in.      *      * @param authors The string of authors or editors in bibtex format to parse.      * @return An AuthorList object representing the given authors.      */
DECL|method|getAuthors (String authors)
specifier|public
specifier|static
name|AuthorList
name|getAuthors
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|authors
argument_list|)
expr_stmt|;
name|AuthorList
name|authorList
init|=
name|AUTHOR_CACHE
operator|.
name|get
argument_list|(
name|authors
argument_list|)
decl_stmt|;
if|if
condition|(
name|authorList
operator|==
literal|null
condition|)
block|{
name|AuthorListParser
name|parser
init|=
operator|new
name|AuthorListParser
argument_list|()
decl_stmt|;
name|authorList
operator|=
name|parser
operator|.
name|parse
argument_list|(
name|authors
argument_list|)
expr_stmt|;
name|AUTHOR_CACHE
operator|.
name|put
argument_list|(
name|authors
argument_list|,
name|authorList
argument_list|)
expr_stmt|;
block|}
return|return
name|authorList
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsFirstFirst()      *      * @see AuthorList#getAsFirstLastNames      */
DECL|method|fixAuthorFirstNameFirstCommas (String authors, boolean abbr, boolean oxfordComma)
specifier|public
specifier|static
name|String
name|fixAuthorFirstNameFirstCommas
parameter_list|(
name|String
name|authors
parameter_list|,
name|boolean
name|abbr
parameter_list|,
name|boolean
name|oxfordComma
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getAsFirstLastNames
argument_list|(
name|abbr
argument_list|,
name|oxfordComma
argument_list|)
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsFirstFirstAnds()      *      * @see AuthorList#getAsFirstLastNamesWithAnd      */
DECL|method|fixAuthorFirstNameFirst (String authors)
specifier|public
specifier|static
name|String
name|fixAuthorFirstNameFirst
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getAsFirstLastNamesWithAnd
argument_list|()
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsLastFirst()      *      * @see AuthorList#getAsLastFirstNames      */
DECL|method|fixAuthorLastNameFirstCommas (String authors, boolean abbr, boolean oxfordComma)
specifier|public
specifier|static
name|String
name|fixAuthorLastNameFirstCommas
parameter_list|(
name|String
name|authors
parameter_list|,
name|boolean
name|abbr
parameter_list|,
name|boolean
name|oxfordComma
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getAsLastFirstNames
argument_list|(
name|abbr
argument_list|,
name|oxfordComma
argument_list|)
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsLastFirstAnds(true)      *      * @see AuthorList#getAsLastFirstNamesWithAnd      */
DECL|method|fixAuthorLastNameFirst (String authors)
specifier|public
specifier|static
name|String
name|fixAuthorLastNameFirst
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getAsLastFirstNamesWithAnd
argument_list|(
literal|false
argument_list|)
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsLastFirstAnds()      *      * @see AuthorList#getAsLastFirstNamesWithAnd      */
DECL|method|fixAuthorLastNameFirst (String authors, boolean abbreviate)
specifier|public
specifier|static
name|String
name|fixAuthorLastNameFirst
parameter_list|(
name|String
name|authors
parameter_list|,
name|boolean
name|abbreviate
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getAsLastFirstNamesWithAnd
argument_list|(
name|abbreviate
argument_list|)
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsLastOnly()      *      * @see AuthorList#getAsLastNames      */
DECL|method|fixAuthorLastNameOnlyCommas (String authors, boolean oxfordComma)
specifier|public
specifier|static
name|String
name|fixAuthorLastNameOnlyCommas
parameter_list|(
name|String
name|authors
parameter_list|,
name|boolean
name|oxfordComma
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getAsLastNames
argument_list|(
name|oxfordComma
argument_list|)
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsForAlphabetization()      *      * @see AuthorList#getForAlphabetization      */
DECL|method|fixAuthorForAlphabetization (String authors)
specifier|public
specifier|static
name|String
name|fixAuthorForAlphabetization
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getForAlphabetization
argument_list|()
return|;
block|}
comment|/**      * This is a convenience method for getAuthorsNatbib()      *      * @see AuthorList#getAsNatbib      */
DECL|method|fixAuthorNatbib (String authors)
specifier|public
specifier|static
name|String
name|fixAuthorNatbib
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
return|return
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|authors
argument_list|)
operator|.
name|getAsNatbib
argument_list|()
return|;
block|}
comment|/**      * Returns the number of author names in this object.      *      * @return the number of author names in this object.      */
DECL|method|getNumberOfAuthors ()
specifier|public
name|int
name|getNumberOfAuthors
parameter_list|()
block|{
return|return
name|authors
operator|.
name|size
argument_list|()
return|;
block|}
comment|/**      * Returns true if there are no authors in the list.      *      * @return true if there are no authors in the list.      */
DECL|method|isEmpty ()
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|authors
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/**      * Returns the<CODE>Author</CODE> object for the i-th author.      *      * @param i Index of the author (from 0 to<CODE>size()-1</CODE>).      * @return the<CODE>Author</CODE> object.      */
DECL|method|getAuthor (int i)
specifier|public
name|Author
name|getAuthor
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
name|authors
operator|.
name|get
argument_list|(
name|i
argument_list|)
return|;
block|}
comment|/**      * Returns the a list of<CODE>Author</CODE> objects.      *      * @return the<CODE>List<Author></CODE> object.      */
DECL|method|getAuthors ()
specifier|public
name|List
argument_list|<
name|Author
argument_list|>
name|getAuthors
parameter_list|()
block|{
return|return
name|authors
return|;
block|}
comment|/**      * Returns the list of authors in "natbib" format.      *<p>      *<ul>      *<li>"John Smith" -> "Smith"</li>      *<li>"John Smith and Black Brown, Peter" ==> "Smith and Black Brown"</li>      *<li>"John von Neumann and John Smith and Black Brown, Peter" ==> "von      * Neumann et al."</li>      *</ul>      *      * @return formatted list of authors.      */
DECL|method|getAsNatbib ()
specifier|public
name|String
name|getAsNatbib
parameter_list|()
block|{
comment|// Check if we've computed this before:
if|if
condition|(
name|authorsNatbib
operator|!=
literal|null
condition|)
block|{
return|return
name|authorsNatbib
return|;
block|}
name|StringBuilder
name|res
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|isEmpty
argument_list|()
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
literal|0
argument_list|)
operator|.
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getNumberOfAuthors
argument_list|()
operator|==
literal|2
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
literal|1
argument_list|)
operator|.
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|getNumberOfAuthors
argument_list|()
operator|>
literal|2
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|" et al."
argument_list|)
expr_stmt|;
block|}
block|}
name|authorsNatbib
operator|=
name|res
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|authorsNatbib
return|;
block|}
comment|/**      * Returns the list of authors separated by commas with last name only; If      * the list consists of two or more authors, "and" is inserted before the      * last author's name.      *<p>      *<p>      *<ul>      *<li> "John Smith" ==> "Smith"</li>      *<li> "John Smith and Black Brown, Peter" ==> "Smith and Black Brown"</li>      *<li> "John von Neumann and John Smith and Black Brown, Peter" ==> "von      * Neumann, Smith and Black Brown".</li>      *</ul>      *      * @param oxfordComma Whether to put a comma before the and at the end.      * @return formatted list of authors.      * @see<a href="http://en.wikipedia.org/wiki/Serial_comma">serial comma for an detailed explaination about the Oxford comma.</a>      */
DECL|method|getAsLastNames (boolean oxfordComma)
specifier|public
name|String
name|getAsLastNames
parameter_list|(
name|boolean
name|oxfordComma
parameter_list|)
block|{
name|int
name|abbrInt
init|=
name|oxfordComma
condition|?
literal|0
else|:
literal|1
decl_stmt|;
comment|// Check if we've computed this before:
if|if
condition|(
name|authorsLastOnly
index|[
name|abbrInt
index|]
operator|!=
literal|null
condition|)
block|{
return|return
name|authorsLastOnly
index|[
name|abbrInt
index|]
return|;
block|}
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
literal|0
argument_list|)
operator|.
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|i
operator|<
operator|(
name|getNumberOfAuthors
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|getNumberOfAuthors
argument_list|()
operator|>
literal|2
operator|)
operator|&&
name|oxfordComma
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getNumberOfAuthors
argument_list|()
operator|>
literal|1
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|authorsLastOnly
index|[
name|abbrInt
index|]
operator|=
name|result
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|authorsLastOnly
index|[
name|abbrInt
index|]
return|;
block|}
comment|/**      * Returns the list of authors separated by commas with first names after      * last name; first names are abbreviated or not depending on parameter. If      * the list consists of three or more authors, "and" is inserted before the      * last author's name.      *<p>      *<p>      *<ul>      *<li> "John Smith" ==> "Smith, John" or "Smith, J."</li>      *<li> "John Smith and Black Brown, Peter" ==> "Smith, John and Black      * Brown, Peter" or "Smith, J. and Black Brown, P."</li>      *<li> "John von Neumann and John Smith and Black Brown, Peter" ==> "von      * Neumann, John, Smith, John and Black Brown, Peter" or "von Neumann, J.,      * Smith, J. and Black Brown, P.".</li>      *</ul>      *      * @param abbreviate  whether to abbreivate first names.      * @param oxfordComma Whether to put a comma before the and at the end.      * @return formatted list of authors.      * @see<a href="http://en.wikipedia.org/wiki/Serial_comma">serial comma for an detailed explaination about the Oxford comma.</a>      */
DECL|method|getAsLastFirstNames (boolean abbreviate, boolean oxfordComma)
specifier|public
name|String
name|getAsLastFirstNames
parameter_list|(
name|boolean
name|abbreviate
parameter_list|,
name|boolean
name|oxfordComma
parameter_list|)
block|{
name|int
name|abbrInt
init|=
name|abbreviate
condition|?
literal|0
else|:
literal|1
decl_stmt|;
name|abbrInt
operator|+=
name|oxfordComma
condition|?
literal|0
else|:
literal|2
expr_stmt|;
comment|// Check if we've computed this before:
if|if
condition|(
name|authorsLastFirst
index|[
name|abbrInt
index|]
operator|!=
literal|null
condition|)
block|{
return|return
name|authorsLastFirst
index|[
name|abbrInt
index|]
return|;
block|}
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
literal|0
argument_list|)
operator|.
name|getLastFirst
argument_list|(
name|abbreviate
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|i
operator|<
operator|(
name|getNumberOfAuthors
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getLastFirst
argument_list|(
name|abbreviate
argument_list|)
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|getNumberOfAuthors
argument_list|()
operator|>
literal|2
operator|)
operator|&&
name|oxfordComma
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getNumberOfAuthors
argument_list|()
operator|>
literal|1
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getLastFirst
argument_list|(
name|abbreviate
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|authorsLastFirst
index|[
name|abbrInt
index|]
operator|=
name|result
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|authorsLastFirst
index|[
name|abbrInt
index|]
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getAsLastFirstNamesWithAnd
argument_list|(
literal|false
argument_list|)
return|;
block|}
comment|/**      * Returns the list of authors separated by "and"s with first names after      * last name; first names are not abbreviated.      *<p>      *<ul>      *<li>"John Smith" ==> "Smith, John"</li>      *<li>"John Smith and Black Brown, Peter" ==> "Smith, John and Black      * Brown, Peter"</li>      *<li>"John von Neumann and John Smith and Black Brown, Peter" ==> "von      * Neumann, John and Smith, John and Black Brown, Peter".</li>      *</ul>      *      * @return formatted list of authors.      */
DECL|method|getAsLastFirstNamesWithAnd (boolean abbreviate)
specifier|public
name|String
name|getAsLastFirstNamesWithAnd
parameter_list|(
name|boolean
name|abbreviate
parameter_list|)
block|{
name|int
name|abbrInt
init|=
name|abbreviate
condition|?
literal|0
else|:
literal|1
decl_stmt|;
comment|// Check if we've computed this before:
if|if
condition|(
name|authorLastFirstAnds
index|[
name|abbrInt
index|]
operator|!=
literal|null
condition|)
block|{
return|return
name|authorLastFirstAnds
index|[
name|abbrInt
index|]
return|;
block|}
name|authorLastFirstAnds
index|[
name|abbrInt
index|]
operator|=
name|getAuthors
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|author
lambda|->
name|author
operator|.
name|getLastFirst
argument_list|(
name|abbreviate
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|authorLastFirstAnds
index|[
name|abbrInt
index|]
return|;
block|}
DECL|method|getAsLastFirstFirstLastNamesWithAnd (boolean abbreviate)
specifier|public
name|String
name|getAsLastFirstFirstLastNamesWithAnd
parameter_list|(
name|boolean
name|abbreviate
parameter_list|)
block|{
name|int
name|abbrInt
init|=
name|abbreviate
condition|?
literal|0
else|:
literal|1
decl_stmt|;
comment|// Check if we've computed this before:
if|if
condition|(
name|authorsLastFirstFirstLast
index|[
name|abbrInt
index|]
operator|!=
literal|null
condition|)
block|{
return|return
name|authorsLastFirstFirstLast
index|[
name|abbrInt
index|]
return|;
block|}
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
literal|0
argument_list|)
operator|.
name|getLastFirst
argument_list|(
name|abbreviate
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|getNumberOfAuthors
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getFirstLast
argument_list|(
name|abbreviate
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|authorsLastFirstFirstLast
index|[
name|abbrInt
index|]
operator|=
name|result
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|authorsLastFirstFirstLast
index|[
name|abbrInt
index|]
return|;
block|}
comment|/**      * Returns the list of authors separated by commas with first names before      * last name; first names are abbreviated or not depending on parameter. If      * the list consists of three or more authors, "and" is inserted before the      * last author's name.      *<p>      *<ul>      *<li>"John Smith" ==> "John Smith" or "J. Smith"</li>      *<li>"John Smith and Black Brown, Peter" ==> "John Smith and Peter Black      * Brown" or "J. Smith and P. Black Brown"</li>      *<li> "John von Neumann and John Smith and Black Brown, Peter" ==> "John      * von Neumann, John Smith and Peter Black Brown" or "J. von Neumann, J.      * Smith and P. Black Brown"</li>      *</ul>      *      * @param abbr        whether to abbreivate first names.      * @param oxfordComma Whether to put a comma before the and at the end.      * @return formatted list of authors.      * @see<a href="http://en.wikipedia.org/wiki/Serial_comma">serial comma for an detailed explaination about the Oxford comma.</a>      */
DECL|method|getAsFirstLastNames (boolean abbr, boolean oxfordComma)
specifier|public
name|String
name|getAsFirstLastNames
parameter_list|(
name|boolean
name|abbr
parameter_list|,
name|boolean
name|oxfordComma
parameter_list|)
block|{
name|int
name|abbrInt
init|=
name|abbr
condition|?
literal|0
else|:
literal|1
decl_stmt|;
name|abbrInt
operator|+=
name|oxfordComma
condition|?
literal|0
else|:
literal|2
expr_stmt|;
comment|// Check if we've computed this before:
if|if
condition|(
name|authorsFirstFirst
index|[
name|abbrInt
index|]
operator|!=
literal|null
condition|)
block|{
return|return
name|authorsFirstFirst
index|[
name|abbrInt
index|]
return|;
block|}
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
literal|0
argument_list|)
operator|.
name|getFirstLast
argument_list|(
name|abbr
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|i
operator|<
operator|(
name|getNumberOfAuthors
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getFirstLast
argument_list|(
name|abbr
argument_list|)
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|getNumberOfAuthors
argument_list|()
operator|>
literal|2
operator|)
operator|&&
name|oxfordComma
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getNumberOfAuthors
argument_list|()
operator|>
literal|1
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|" and "
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getFirstLast
argument_list|(
name|abbr
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|authorsFirstFirst
index|[
name|abbrInt
index|]
operator|=
name|result
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|authorsFirstFirst
index|[
name|abbrInt
index|]
return|;
block|}
comment|/**      * Compare this object with the given one.      *<p>      * Will return true iff the other object is an Author and all fields are identical on a string comparison.      */
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|AuthorList
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|AuthorList
name|a
init|=
operator|(
name|AuthorList
operator|)
name|o
decl_stmt|;
return|return
name|this
operator|.
name|authors
operator|.
name|equals
argument_list|(
name|a
operator|.
name|authors
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|authors
argument_list|)
return|;
block|}
comment|/**      * Returns the list of authors separated by "and"s with first names before      * last name; first names are not abbreviated.      *<p>      *<ul>      *<li>"John Smith" ==> "John Smith"</li>      *<li>"John Smith and Black Brown, Peter" ==> "John Smith and Peter Black      * Brown"</li>      *<li>"John von Neumann and John Smith and Black Brown, Peter" ==> "John      * von Neumann and John Smith and Peter Black Brown"</li>      *</li>      *      * @return formatted list of authors.      */
DECL|method|getAsFirstLastNamesWithAnd ()
specifier|public
name|String
name|getAsFirstLastNamesWithAnd
parameter_list|()
block|{
comment|// Check if we've computed this before:
if|if
condition|(
name|authorsFirstFirstAnds
operator|!=
literal|null
condition|)
block|{
return|return
name|authorsFirstFirstAnds
return|;
block|}
name|authorsFirstFirstAnds
operator|=
name|getAuthors
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|author
lambda|->
name|author
operator|.
name|getFirstLast
argument_list|(
literal|false
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|authorsFirstFirstAnds
return|;
block|}
comment|/**      * Returns the list of authors in a form suitable for alphabetization. This      * means that last names come first, never preceded by "von" particles, and      * that any braces are removed. First names are abbreviated so the same name      * is treated similarly if abbreviated in one case and not in another. This      * form is not intended to be suitable for presentation, only for sorting.      *<p>      *<p>      *<ul>      *<li>"John Smith" ==> "Smith, J.";</li>      *      * @return formatted list of authors      */
DECL|method|getForAlphabetization ()
specifier|public
name|String
name|getForAlphabetization
parameter_list|()
block|{
if|if
condition|(
name|authorsAlph
operator|!=
literal|null
condition|)
block|{
return|return
name|authorsAlph
return|;
block|}
name|authorsAlph
operator|=
name|getAuthors
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|author
lambda|->
name|author
operator|.
name|getNameForAlphabetization
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|authorsAlph
return|;
block|}
block|}
end_class

end_unit

