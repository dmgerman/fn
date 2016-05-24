begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_class
DECL|class|AuthorListParser
specifier|public
class|class
name|AuthorListParser
block|{
comment|/** the raw bibtex author/editor field */
DECL|field|original
specifier|private
name|String
name|original
decl_stmt|;
comment|/** index of the start in original, for example to point to 'abc' in 'abc xyz', tokenStart=2 */
DECL|field|tokenStart
specifier|private
name|int
name|tokenStart
decl_stmt|;
comment|/** index of the end in original, for example to point to 'abc' in 'abc xyz', tokenEnd=5 */
DECL|field|tokenEnd
specifier|private
name|int
name|tokenEnd
decl_stmt|;
comment|/** end of token abbreviation (always: tokenStart< tokenAbbr<= tokenEnd), only valid if getToken returns TOKEN_WORD */
DECL|field|tokenAbbr
specifier|private
name|int
name|tokenAbbr
decl_stmt|;
comment|/** either space of dash */
DECL|field|tokenTerm
specifier|private
name|char
name|tokenTerm
decl_stmt|;
comment|/** true if upper-case token, false if lower-case */
DECL|field|tokenCase
specifier|private
name|boolean
name|tokenCase
decl_stmt|;
comment|// Constant HashSet containing names of TeX special characters
DECL|field|TEX_NAMES
specifier|private
specifier|static
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|TEX_NAMES
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
comment|// and static constructor to initialize it
static|static
block|{
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"aa"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"ae"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"l"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"o"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"oe"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"i"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"AA"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"AE"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"L"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"O"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"OE"
argument_list|)
expr_stmt|;
name|TEX_NAMES
operator|.
name|add
argument_list|(
literal|"j"
argument_list|)
expr_stmt|;
block|}
DECL|field|TOKEN_GROUP_LENGTH
specifier|private
specifier|static
specifier|final
name|int
name|TOKEN_GROUP_LENGTH
init|=
literal|4
decl_stmt|;
comment|// number of entries for a token
comment|// the following are offsets of an entry in a group of entries for one token
DECL|field|OFFSET_TOKEN
specifier|private
specifier|static
specifier|final
name|int
name|OFFSET_TOKEN
init|=
literal|0
decl_stmt|;
comment|// String -- token itself;
DECL|field|OFFSET_TOKEN_ABBR
specifier|private
specifier|static
specifier|final
name|int
name|OFFSET_TOKEN_ABBR
init|=
literal|1
decl_stmt|;
comment|// String -- token abbreviation;
DECL|field|OFFSET_TOKEN_TERM
specifier|private
specifier|static
specifier|final
name|int
name|OFFSET_TOKEN_TERM
init|=
literal|2
decl_stmt|;
comment|// Character -- token terminator (either " " or
comment|// "-") comma)
comment|// Token types (returned by getToken procedure)
DECL|field|TOKEN_EOF
specifier|private
specifier|static
specifier|final
name|int
name|TOKEN_EOF
init|=
literal|0
decl_stmt|;
DECL|field|TOKEN_AND
specifier|private
specifier|static
specifier|final
name|int
name|TOKEN_AND
init|=
literal|1
decl_stmt|;
DECL|field|TOKEN_COMMA
specifier|private
specifier|static
specifier|final
name|int
name|TOKEN_COMMA
init|=
literal|2
decl_stmt|;
DECL|field|TOKEN_WORD
specifier|private
specifier|static
specifier|final
name|int
name|TOKEN_WORD
init|=
literal|3
decl_stmt|;
comment|/**      * Parses the String containing person names and returns a list of person information.      *      * @param listOfNames the String containing the person names to be parsed      * @return a parsed list of persons      */
DECL|method|parse (String listOfNames)
specifier|public
name|AuthorList
name|parse
parameter_list|(
name|String
name|listOfNames
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|listOfNames
argument_list|)
expr_stmt|;
comment|// initialization of parser
name|original
operator|=
name|listOfNames
expr_stmt|;
name|tokenStart
operator|=
literal|0
expr_stmt|;
name|tokenEnd
operator|=
literal|0
expr_stmt|;
comment|// Parse author by author
name|List
argument_list|<
name|Author
argument_list|>
name|authors
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|5
argument_list|)
decl_stmt|;
comment|// 5 seems to be reasonable initial size
while|while
condition|(
name|tokenStart
operator|<
name|original
operator|.
name|length
argument_list|()
condition|)
block|{
name|Author
name|author
init|=
name|getAuthor
argument_list|()
decl_stmt|;
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
block|{
name|authors
operator|.
name|add
argument_list|(
name|author
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|AuthorList
argument_list|(
name|authors
argument_list|)
return|;
block|}
comment|/**      * Parses one author name and returns preformatted information.      *      * @return Preformatted author name;<CODE>null</CODE> if author name is      * empty.      */
DECL|method|getAuthor ()
specifier|private
name|Author
name|getAuthor
parameter_list|()
block|{
name|List
argument_list|<
name|Object
argument_list|>
name|tokens
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// initialization
name|int
name|vonStart
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|lastStart
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|commaFirst
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|commaSecond
init|=
operator|-
literal|1
decl_stmt|;
comment|// First step: collect tokens in 'tokens' Vector and calculate indices
name|boolean
name|continueLoop
init|=
literal|true
decl_stmt|;
while|while
condition|(
name|continueLoop
condition|)
block|{
name|int
name|token
init|=
name|getToken
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|token
condition|)
block|{
case|case
name|TOKEN_EOF
case|:
case|case
name|TOKEN_AND
case|:
name|continueLoop
operator|=
literal|false
expr_stmt|;
break|break;
case|case
name|TOKEN_COMMA
case|:
if|if
condition|(
name|commaFirst
operator|<
literal|0
condition|)
block|{
name|commaFirst
operator|=
name|tokens
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|commaSecond
operator|<
literal|0
condition|)
block|{
name|commaSecond
operator|=
name|tokens
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
break|break;
case|case
name|TOKEN_WORD
case|:
name|tokens
operator|.
name|add
argument_list|(
name|original
operator|.
name|substring
argument_list|(
name|tokenStart
argument_list|,
name|tokenEnd
argument_list|)
argument_list|)
expr_stmt|;
name|tokens
operator|.
name|add
argument_list|(
name|original
operator|.
name|substring
argument_list|(
name|tokenStart
argument_list|,
name|tokenAbbr
argument_list|)
argument_list|)
expr_stmt|;
name|tokens
operator|.
name|add
argument_list|(
name|tokenTerm
argument_list|)
expr_stmt|;
name|tokens
operator|.
name|add
argument_list|(
name|tokenCase
argument_list|)
expr_stmt|;
if|if
condition|(
name|commaFirst
operator|>=
literal|0
condition|)
block|{
break|break;
block|}
if|if
condition|(
name|lastStart
operator|>=
literal|0
condition|)
block|{
break|break;
block|}
if|if
condition|(
name|vonStart
operator|<
literal|0
condition|)
block|{
if|if
condition|(
operator|!
name|tokenCase
condition|)
block|{
name|int
name|previousTermToken
init|=
name|tokens
operator|.
name|size
argument_list|()
operator|-
name|TOKEN_GROUP_LENGTH
operator|-
name|TOKEN_GROUP_LENGTH
operator|+
name|OFFSET_TOKEN_TERM
decl_stmt|;
if|if
condition|(
name|previousTermToken
operator|>=
literal|0
operator|&&
name|tokens
operator|.
name|get
argument_list|(
name|previousTermToken
argument_list|)
operator|.
name|equals
argument_list|(
literal|'-'
argument_list|)
condition|)
block|{
comment|// We are in a first name which contained a hyphen
break|break;
block|}
name|vonStart
operator|=
name|tokens
operator|.
name|size
argument_list|()
operator|-
name|TOKEN_GROUP_LENGTH
expr_stmt|;
break|break;
block|}
block|}
elseif|else
if|if
condition|(
operator|(
name|lastStart
operator|<
literal|0
operator|)
operator|&&
name|tokenCase
condition|)
block|{
name|lastStart
operator|=
name|tokens
operator|.
name|size
argument_list|()
operator|-
name|TOKEN_GROUP_LENGTH
expr_stmt|;
break|break;
block|}
break|break;
default|default:
break|break;
block|}
block|}
comment|// Second step: split name into parts (here: calculate indices
comment|// of parts in 'tokens' Vector)
if|if
condition|(
name|tokens
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
comment|// no author information
block|}
comment|// the following negatives indicate absence of the corresponding part
name|int
name|firstPartStart
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|vonPartStart
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|lastPartStart
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|jrPartStart
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|firstPartEnd
decl_stmt|;
name|int
name|vonPartEnd
init|=
literal|0
decl_stmt|;
name|int
name|lastPartEnd
init|=
literal|0
decl_stmt|;
name|int
name|jrPartEnd
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|commaFirst
operator|<
literal|0
condition|)
block|{
comment|// no commas
if|if
condition|(
name|vonStart
operator|<
literal|0
condition|)
block|{
comment|// no 'von part'
name|lastPartEnd
operator|=
name|tokens
operator|.
name|size
argument_list|()
expr_stmt|;
name|lastPartStart
operator|=
name|tokens
operator|.
name|size
argument_list|()
operator|-
name|TOKEN_GROUP_LENGTH
expr_stmt|;
name|int
name|index
init|=
operator|(
name|tokens
operator|.
name|size
argument_list|()
operator|-
operator|(
literal|2
operator|*
name|TOKEN_GROUP_LENGTH
operator|)
operator|)
operator|+
name|OFFSET_TOKEN_TERM
decl_stmt|;
if|if
condition|(
name|index
operator|>
literal|0
condition|)
block|{
name|Character
name|ch
init|=
operator|(
name|Character
operator|)
name|tokens
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|ch
operator|==
literal|'-'
condition|)
block|{
name|lastPartStart
operator|-=
name|TOKEN_GROUP_LENGTH
expr_stmt|;
block|}
block|}
name|firstPartEnd
operator|=
name|lastPartStart
expr_stmt|;
if|if
condition|(
name|firstPartEnd
operator|>
literal|0
condition|)
block|{
name|firstPartStart
operator|=
literal|0
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// 'von part' is present
if|if
condition|(
name|lastStart
operator|>=
literal|0
condition|)
block|{
name|lastPartEnd
operator|=
name|tokens
operator|.
name|size
argument_list|()
expr_stmt|;
name|lastPartStart
operator|=
name|lastStart
expr_stmt|;
name|vonPartEnd
operator|=
name|lastPartStart
expr_stmt|;
block|}
else|else
block|{
name|vonPartEnd
operator|=
name|tokens
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
name|vonPartStart
operator|=
name|vonStart
expr_stmt|;
name|firstPartEnd
operator|=
name|vonPartStart
expr_stmt|;
if|if
condition|(
name|firstPartEnd
operator|>
literal|0
condition|)
block|{
name|firstPartStart
operator|=
literal|0
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// commas are present: it affects only 'first part' and
comment|// 'junior part'
name|firstPartEnd
operator|=
name|tokens
operator|.
name|size
argument_list|()
expr_stmt|;
if|if
condition|(
name|commaSecond
operator|<
literal|0
condition|)
block|{
comment|// one comma
if|if
condition|(
name|commaFirst
operator|<
name|firstPartEnd
condition|)
block|{
name|firstPartStart
operator|=
name|commaFirst
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// two or more commas
if|if
condition|(
name|commaSecond
operator|<
name|firstPartEnd
condition|)
block|{
name|firstPartStart
operator|=
name|commaSecond
expr_stmt|;
block|}
name|jrPartEnd
operator|=
name|commaSecond
expr_stmt|;
if|if
condition|(
name|commaFirst
operator|<
name|jrPartEnd
condition|)
block|{
name|jrPartStart
operator|=
name|commaFirst
expr_stmt|;
block|}
block|}
if|if
condition|(
name|vonStart
operator|==
literal|0
condition|)
block|{
comment|// 'von part' is present
if|if
condition|(
name|lastStart
operator|<
literal|0
condition|)
block|{
name|vonPartEnd
operator|=
name|commaFirst
expr_stmt|;
block|}
else|else
block|{
name|lastPartEnd
operator|=
name|commaFirst
expr_stmt|;
name|lastPartStart
operator|=
name|lastStart
expr_stmt|;
name|vonPartEnd
operator|=
name|lastPartStart
expr_stmt|;
block|}
name|vonPartStart
operator|=
literal|0
expr_stmt|;
block|}
else|else
block|{
comment|// no 'von part'
name|lastPartEnd
operator|=
name|commaFirst
expr_stmt|;
if|if
condition|(
name|lastPartEnd
operator|>
literal|0
condition|)
block|{
name|lastPartStart
operator|=
literal|0
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|(
name|firstPartStart
operator|==
operator|-
literal|1
operator|)
operator|&&
operator|(
name|lastPartStart
operator|==
operator|-
literal|1
operator|)
operator|&&
operator|(
name|vonPartStart
operator|!=
operator|-
literal|1
operator|)
condition|)
block|{
comment|// There is no first or last name, but we have a von part. This is likely
comment|// to indicate a single-entry name without an initial capital letter, such
comment|// as "unknown".
comment|// We make the von part the last name, to facilitate handling by last-name formatters:
name|lastPartStart
operator|=
name|vonPartStart
expr_stmt|;
name|lastPartEnd
operator|=
name|vonPartEnd
expr_stmt|;
name|vonPartStart
operator|=
operator|-
literal|1
expr_stmt|;
name|vonPartEnd
operator|=
operator|-
literal|1
expr_stmt|;
block|}
comment|// Third step: do actual splitting, construct Author object
name|String
name|firstPart
init|=
name|firstPartStart
operator|<
literal|0
condition|?
literal|null
else|:
name|concatTokens
argument_list|(
name|tokens
argument_list|,
name|firstPartStart
argument_list|,
name|firstPartEnd
argument_list|,
name|OFFSET_TOKEN
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|String
name|firstAbbr
init|=
name|firstPartStart
operator|<
literal|0
condition|?
literal|null
else|:
name|concatTokens
argument_list|(
name|tokens
argument_list|,
name|firstPartStart
argument_list|,
name|firstPartEnd
argument_list|,
name|OFFSET_TOKEN_ABBR
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|String
name|vonPart
init|=
name|vonPartStart
operator|<
literal|0
condition|?
literal|null
else|:
name|concatTokens
argument_list|(
name|tokens
argument_list|,
name|vonPartStart
argument_list|,
name|vonPartEnd
argument_list|,
name|OFFSET_TOKEN
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|String
name|lastPart
init|=
name|lastPartStart
operator|<
literal|0
condition|?
literal|null
else|:
name|concatTokens
argument_list|(
name|tokens
argument_list|,
name|lastPartStart
argument_list|,
name|lastPartEnd
argument_list|,
name|OFFSET_TOKEN
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|String
name|jrPart
init|=
name|jrPartStart
operator|<
literal|0
condition|?
literal|null
else|:
name|concatTokens
argument_list|(
name|tokens
argument_list|,
name|jrPartStart
argument_list|,
name|jrPartEnd
argument_list|,
name|OFFSET_TOKEN
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|firstPart
operator|!=
literal|null
operator|&&
name|lastPart
operator|!=
literal|null
operator|&&
name|lastPart
operator|.
name|equals
argument_list|(
name|lastPart
operator|.
name|toUpperCase
argument_list|()
argument_list|)
operator|&&
name|lastPart
operator|.
name|length
argument_list|()
operator|<
literal|5
condition|)
block|{
comment|// The last part is a small string in complete upper case, so interpret it as initial of the first name
comment|// This is the case for example in "Smith SH" which we think of as lastname=Smith and firstname=SH
comment|// The length< 5 constraint should allow for "Smith S.H." as input
return|return
operator|new
name|Author
argument_list|(
name|lastPart
argument_list|,
name|lastPart
argument_list|,
name|vonPart
argument_list|,
name|firstPart
argument_list|,
name|jrPart
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|Author
argument_list|(
name|firstPart
argument_list|,
name|firstAbbr
argument_list|,
name|vonPart
argument_list|,
name|lastPart
argument_list|,
name|jrPart
argument_list|)
return|;
block|}
block|}
comment|/**      * Concatenates list of tokens from 'tokens' Vector. Tokens are separated by      * spaces or dashes, depending on stored in 'tokens'. Callers always ensure      * that start< end; thus, there exists at least one token to be      * concatenated.      *      * @param start     index of the first token to be concatenated in 'tokens' Vector      *                  (always divisible by TOKEN_GROUP_LENGTH).      * @param end       index of the first token not to be concatenated in 'tokens'      *                  Vector (always divisible by TOKEN_GROUP_LENGTH).      * @param offset    offset within token group (used to request concatenation of      *                  either full tokens or abbreviation).      * @param dotAfter<CODE>true</CODE> -- add period after each token,<CODE>false</CODE> --      *                  do not add.      * @return the result of concatenation.      */
DECL|method|concatTokens (List<Object> tokens, int start, int end, int offset, boolean dotAfter)
specifier|private
name|String
name|concatTokens
parameter_list|(
name|List
argument_list|<
name|Object
argument_list|>
name|tokens
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|,
name|int
name|offset
parameter_list|,
name|boolean
name|dotAfter
parameter_list|)
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// Here we always have start< end
name|result
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|tokens
operator|.
name|get
argument_list|(
name|start
operator|+
name|offset
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|dotAfter
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
block|}
name|int
name|updatedStart
init|=
name|start
operator|+
name|TOKEN_GROUP_LENGTH
decl_stmt|;
while|while
condition|(
name|updatedStart
operator|<
name|end
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|tokens
operator|.
name|get
argument_list|(
operator|(
name|updatedStart
operator|-
name|TOKEN_GROUP_LENGTH
operator|)
operator|+
name|OFFSET_TOKEN_TERM
argument_list|)
argument_list|)
expr_stmt|;
name|result
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|tokens
operator|.
name|get
argument_list|(
name|updatedStart
operator|+
name|offset
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|dotAfter
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
literal|'.'
argument_list|)
expr_stmt|;
block|}
name|updatedStart
operator|+=
name|TOKEN_GROUP_LENGTH
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Parses the next token.      *<p>      * The string being parsed is stored in global variable<CODE>orig</CODE>,      * and position which parsing has to start from is stored in global variable      *<CODE>token_end</CODE>; thus,<CODE>token_end</CODE> has to be set      * to 0 before the first invocation. Procedure updates<CODE>token_end</CODE>;      * thus, subsequent invocations do not require any additional variable      * settings.      *<p>      * The type of the token is returned; if it is<CODE>TOKEN_WORD</CODE>,      * additional information is given in global variables<CODE>token_start</CODE>,      *<CODE>token_end</CODE>,<CODE>token_abbr</CODE>,<CODE>token_term</CODE>,      * and<CODE>token_case</CODE>; namely:<CODE>orig.substring(token_start,token_end)</CODE>      * is the thext of the token,<CODE>orig.substring(token_start,token_abbr)</CODE>      * is the token abbreviation,<CODE>token_term</CODE> contains token      * terminator (space or dash), and<CODE>token_case</CODE> is<CODE>true</CODE>,      * if token is upper-case and<CODE>false</CODE> if token is lower-case.      *      * @return<CODE>TOKEN_EOF</CODE> -- no more tokens,<CODE>TOKEN_COMMA</CODE> --      * token is comma,<CODE>TOKEN_AND</CODE> -- token is the word      * "and" (or "And", or "aND", etc.) or a colon,<CODE>TOKEN_WORD</CODE> --      * token is a word; additional information is given in global      * variables<CODE>token_start</CODE>,<CODE>token_end</CODE>,      *<CODE>token_abbr</CODE>,<CODE>token_term</CODE>, and      *<CODE>token_case</CODE>.      */
DECL|method|getToken ()
specifier|private
name|int
name|getToken
parameter_list|()
block|{
name|tokenStart
operator|=
name|tokenEnd
expr_stmt|;
while|while
condition|(
name|tokenStart
operator|<
name|original
operator|.
name|length
argument_list|()
condition|)
block|{
name|char
name|c
init|=
name|original
operator|.
name|charAt
argument_list|(
name|tokenStart
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
operator|(
name|c
operator|==
literal|'~'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'-'
operator|)
operator|||
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|)
condition|)
block|{
break|break;
block|}
name|tokenStart
operator|++
expr_stmt|;
block|}
name|tokenEnd
operator|=
name|tokenStart
expr_stmt|;
if|if
condition|(
name|tokenStart
operator|>=
name|original
operator|.
name|length
argument_list|()
condition|)
block|{
return|return
name|TOKEN_EOF
return|;
block|}
if|if
condition|(
name|original
operator|.
name|charAt
argument_list|(
name|tokenStart
argument_list|)
operator|==
literal|','
condition|)
block|{
name|tokenEnd
operator|++
expr_stmt|;
return|return
name|TOKEN_COMMA
return|;
block|}
comment|// Colon is considered to separate names like "and"
if|if
condition|(
name|original
operator|.
name|charAt
argument_list|(
name|tokenStart
argument_list|)
operator|==
literal|';'
condition|)
block|{
name|tokenEnd
operator|++
expr_stmt|;
return|return
name|TOKEN_AND
return|;
block|}
name|tokenAbbr
operator|=
operator|-
literal|1
expr_stmt|;
name|tokenTerm
operator|=
literal|' '
expr_stmt|;
name|tokenCase
operator|=
literal|true
expr_stmt|;
name|int
name|bracesLevel
init|=
literal|0
decl_stmt|;
name|int
name|currentBackslash
init|=
operator|-
literal|1
decl_stmt|;
name|boolean
name|firstLetterIsFound
init|=
literal|false
decl_stmt|;
while|while
condition|(
name|tokenEnd
operator|<
name|original
operator|.
name|length
argument_list|()
condition|)
block|{
name|char
name|c
init|=
name|original
operator|.
name|charAt
argument_list|(
name|tokenEnd
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|bracesLevel
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|firstLetterIsFound
operator|&&
operator|(
name|tokenAbbr
operator|<
literal|0
operator|)
operator|&&
operator|(
name|bracesLevel
operator|==
literal|0
operator|||
name|c
operator|==
literal|'{'
operator|)
condition|)
block|{
name|tokenAbbr
operator|=
name|tokenEnd
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|c
operator|==
literal|'}'
operator|)
operator|&&
operator|(
name|bracesLevel
operator|>
literal|0
operator|)
condition|)
block|{
name|bracesLevel
operator|--
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|firstLetterIsFound
operator|&&
operator|(
name|currentBackslash
operator|<
literal|0
operator|)
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
name|c
argument_list|)
condition|)
block|{
if|if
condition|(
name|bracesLevel
operator|==
literal|0
condition|)
block|{
name|tokenCase
operator|=
name|Character
operator|.
name|isUpperCase
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// If this is a particle in braces, always treat it as if it starts with
comment|// an upper case letter. Otherwise a name such as "{van den Bergen}, Hans"
comment|// will not yield a proper last name:
name|tokenCase
operator|=
literal|true
expr_stmt|;
block|}
name|firstLetterIsFound
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|currentBackslash
operator|>=
literal|0
operator|)
operator|&&
operator|!
name|Character
operator|.
name|isLetter
argument_list|(
name|c
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|firstLetterIsFound
condition|)
block|{
name|String
name|texCmdName
init|=
name|original
operator|.
name|substring
argument_list|(
name|currentBackslash
operator|+
literal|1
argument_list|,
name|tokenEnd
argument_list|)
decl_stmt|;
if|if
condition|(
name|TEX_NAMES
operator|.
name|contains
argument_list|(
name|texCmdName
argument_list|)
condition|)
block|{
name|tokenCase
operator|=
name|Character
operator|.
name|isUpperCase
argument_list|(
name|texCmdName
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|firstLetterIsFound
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|currentBackslash
operator|=
operator|-
literal|1
expr_stmt|;
block|}
if|if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
name|currentBackslash
operator|=
name|tokenEnd
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|bracesLevel
operator|==
literal|0
operator|)
operator|&&
operator|(
operator|(
literal|",;~-"
operator|.
name|indexOf
argument_list|(
name|c
argument_list|)
operator|!=
operator|-
literal|1
operator|)
operator|||
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|)
condition|)
block|{
break|break;
block|}
name|tokenEnd
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|tokenAbbr
operator|<
literal|0
condition|)
block|{
name|tokenAbbr
operator|=
name|tokenEnd
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|tokenEnd
operator|<
name|original
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
name|original
operator|.
name|charAt
argument_list|(
name|tokenEnd
argument_list|)
operator|==
literal|'-'
operator|)
condition|)
block|{
name|tokenTerm
operator|=
literal|'-'
expr_stmt|;
block|}
if|if
condition|(
literal|"and"
operator|.
name|equalsIgnoreCase
argument_list|(
name|original
operator|.
name|substring
argument_list|(
name|tokenStart
argument_list|,
name|tokenEnd
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|TOKEN_AND
return|;
block|}
else|else
block|{
return|return
name|TOKEN_WORD
return|;
block|}
block|}
block|}
end_class

end_unit

