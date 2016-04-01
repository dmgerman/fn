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
name|Objects
import|;
end_import

begin_comment
comment|/**  * This is an immutable class that keeps information regarding single  * author. It is just a container for the information, with very simple  * methods to access it.  *<p>  * Current usage: only methods<code>getLastOnly</code>,  *<code>getFirstLast</code>, and<code>getLastFirst</code> are used;  * all other methods are provided for completeness.  */
end_comment

begin_class
DECL|class|Author
specifier|public
class|class
name|Author
block|{
DECL|field|firstPart
specifier|private
specifier|final
name|String
name|firstPart
decl_stmt|;
DECL|field|firstAbbr
specifier|private
specifier|final
name|String
name|firstAbbr
decl_stmt|;
DECL|field|vonPart
specifier|private
specifier|final
name|String
name|vonPart
decl_stmt|;
DECL|field|lastPart
specifier|private
specifier|final
name|String
name|lastPart
decl_stmt|;
DECL|field|jrPart
specifier|private
specifier|final
name|String
name|jrPart
decl_stmt|;
comment|/**      * Creates the Author object. If any part of the name is absent,<CODE>null</CODE>      * must be passed; otherwise other methods may return erroneous results.      *      * @param first     the first name of the author (may consist of several      *                  tokens, like "Charles Louis Xavier Joseph" in "Charles      *                  Louis Xavier Joseph de la Vall{\'e}e Poussin")      * @param firstabbr the abbreviated first name of the author (may consist of      *                  several tokens, like "C. L. X. J." in "Charles Louis      *                  Xavier Joseph de la Vall{\'e}e Poussin"). It is a      *                  responsibility of the caller to create a reasonable      *                  abbreviation of the first name.      * @param von       the von part of the author's name (may consist of several      *                  tokens, like "de la" in "Charles Louis Xavier Joseph de la      *                  Vall{\'e}e Poussin")      * @param last      the lats name of the author (may consist of several      *                  tokens, like "Vall{\'e}e Poussin" in "Charles Louis Xavier      *                  Joseph de la Vall{\'e}e Poussin")      * @param jr        the junior part of the author's name (may consist of      *                  several tokens, like "Jr. III" in "Smith, Jr. III, John")      */
DECL|method|Author (String first, String firstabbr, String von, String last, String jr)
specifier|public
name|Author
parameter_list|(
name|String
name|first
parameter_list|,
name|String
name|firstabbr
parameter_list|,
name|String
name|von
parameter_list|,
name|String
name|last
parameter_list|,
name|String
name|jr
parameter_list|)
block|{
name|firstPart
operator|=
name|removeStartAndEndBraces
argument_list|(
name|first
argument_list|)
expr_stmt|;
name|firstAbbr
operator|=
name|removeStartAndEndBraces
argument_list|(
name|firstabbr
argument_list|)
expr_stmt|;
name|vonPart
operator|=
name|removeStartAndEndBraces
argument_list|(
name|von
argument_list|)
expr_stmt|;
name|lastPart
operator|=
name|removeStartAndEndBraces
argument_list|(
name|last
argument_list|)
expr_stmt|;
name|jrPart
operator|=
name|removeStartAndEndBraces
argument_list|(
name|jr
argument_list|)
expr_stmt|;
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
name|firstAbbr
argument_list|,
name|firstPart
argument_list|,
name|jrPart
argument_list|,
name|lastPart
argument_list|,
name|vonPart
argument_list|)
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
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|o
operator|instanceof
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Author
condition|)
block|{
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Author
name|a
init|=
operator|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Author
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|firstPart
argument_list|,
name|a
operator|.
name|firstPart
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|firstAbbr
argument_list|,
name|a
operator|.
name|firstAbbr
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|vonPart
argument_list|,
name|a
operator|.
name|vonPart
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|lastPart
argument_list|,
name|a
operator|.
name|lastPart
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|jrPart
argument_list|,
name|a
operator|.
name|jrPart
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * @return true if the brackets in s are properly paired      */
DECL|method|properBrackets (String s)
specifier|private
name|boolean
name|properBrackets
parameter_list|(
name|String
name|s
parameter_list|)
block|{
comment|// nested construct is there, check for "proper" nesting
name|int
name|i
init|=
literal|0
decl_stmt|;
name|int
name|level
init|=
literal|0
decl_stmt|;
name|loop
label|:
while|while
condition|(
name|i
operator|<
name|s
operator|.
name|length
argument_list|()
condition|)
block|{
name|char
name|c
init|=
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|c
condition|)
block|{
case|case
literal|'{'
case|:
name|level
operator|++
expr_stmt|;
break|break;
case|case
literal|'}'
case|:
name|level
operator|--
expr_stmt|;
if|if
condition|(
name|level
operator|==
operator|-
literal|1
condition|)
block|{
comment|// the improper nesting
break|break
name|loop
break|;
block|}
break|break;
default|default:
break|break;
block|}
name|i
operator|++
expr_stmt|;
block|}
return|return
name|level
operator|==
literal|0
return|;
block|}
comment|/**      * Removes start and end brace at a string      *<p>      * E.g.,      * * {Vall{\'e}e Poussin} -> Vall{\'e}e Poussin      * * {Vall{\'e}e} {Poussin} -> Vall{\'e}e Poussin      * * Vall{\'e}e Poussin -> Vall{\'e}e Poussin      */
DECL|method|removeStartAndEndBraces (String name)
specifier|private
name|String
name|removeStartAndEndBraces
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|!
name|name
operator|.
name|contains
argument_list|(
literal|"{"
argument_list|)
condition|)
block|{
return|return
name|name
return|;
block|}
name|String
index|[]
name|split
init|=
name|name
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|StringBuilder
name|b
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|split
control|)
block|{
if|if
condition|(
operator|(
name|s
operator|.
name|length
argument_list|()
operator|>
literal|2
operator|)
operator|&&
name|s
operator|.
name|startsWith
argument_list|(
literal|"{"
argument_list|)
operator|&&
name|s
operator|.
name|endsWith
argument_list|(
literal|"}"
argument_list|)
condition|)
block|{
comment|// quick solution (which we don't do: just remove first "{" and last "}"
comment|// however, it might be that s is like {A}bbb{c}, where braces may not be removed
comment|// inner
name|String
name|inner
init|=
name|s
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|s
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|inner
operator|.
name|contains
argument_list|(
literal|"}"
argument_list|)
condition|)
block|{
if|if
condition|(
name|properBrackets
argument_list|(
name|inner
argument_list|)
condition|)
block|{
name|s
operator|=
name|inner
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//  no inner curly brackets found, no check needed, inner can just be used as s
name|s
operator|=
name|inner
expr_stmt|;
block|}
block|}
name|b
operator|.
name|append
argument_list|(
name|s
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
comment|// delete last
name|b
operator|.
name|deleteCharAt
argument_list|(
name|b
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|// now, all inner words are cleared
comment|// case {word word word} remains
comment|// as above, we have to be aware of {w}ord word wor{d} and {{w}ord word word}
name|String
name|newName
init|=
name|b
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|newName
operator|.
name|startsWith
argument_list|(
literal|"{"
argument_list|)
operator|&&
name|newName
operator|.
name|endsWith
argument_list|(
literal|"}"
argument_list|)
condition|)
block|{
name|String
name|inner
init|=
name|newName
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|newName
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|properBrackets
argument_list|(
name|inner
argument_list|)
condition|)
block|{
return|return
name|inner
return|;
block|}
else|else
block|{
return|return
name|newName
return|;
block|}
block|}
else|else
block|{
return|return
name|newName
return|;
block|}
block|}
comment|/**      * Returns the first name of the author stored in this object ("First").      *      * @return first name of the author (may consist of several tokens)      */
DECL|method|getFirst ()
specifier|public
name|String
name|getFirst
parameter_list|()
block|{
return|return
name|firstPart
return|;
block|}
comment|/**      * Returns the abbreviated first name of the author stored in this      * object ("F.").      *      * @return abbreviated first name of the author (may consist of several      * tokens)      */
DECL|method|getFirstAbbr ()
specifier|public
name|String
name|getFirstAbbr
parameter_list|()
block|{
return|return
name|firstAbbr
return|;
block|}
comment|/**      * Returns the von part of the author's name stored in this object      * ("von").      *      * @return von part of the author's name (may consist of several tokens)      */
DECL|method|getVon ()
specifier|public
name|String
name|getVon
parameter_list|()
block|{
return|return
name|vonPart
return|;
block|}
comment|/**      * Returns the last name of the author stored in this object ("Last").      *      * @return last name of the author (may consist of several tokens)      */
DECL|method|getLast ()
specifier|public
name|String
name|getLast
parameter_list|()
block|{
return|return
name|lastPart
return|;
block|}
comment|/**      * Returns the junior part of the author's name stored in this object      * ("Jr").      *      * @return junior part of the author's name (may consist of several      * tokens) or null if the author does not have a Jr. Part      */
DECL|method|getJr ()
specifier|public
name|String
name|getJr
parameter_list|()
block|{
return|return
name|jrPart
return|;
block|}
comment|/**      * Returns von-part followed by last name ("von Last"). If both fields      * were specified as<CODE>null</CODE>, the empty string<CODE>""</CODE>      * is returned.      *      * @return 'von Last'      */
DECL|method|getLastOnly ()
specifier|public
name|String
name|getLastOnly
parameter_list|()
block|{
if|if
condition|(
name|vonPart
operator|==
literal|null
condition|)
block|{
return|return
name|lastPart
operator|==
literal|null
condition|?
literal|""
else|:
name|lastPart
return|;
block|}
else|else
block|{
return|return
name|lastPart
operator|==
literal|null
condition|?
name|vonPart
else|:
name|vonPart
operator|+
literal|' '
operator|+
name|lastPart
return|;
block|}
block|}
comment|/**      * Returns the author's name in form 'von Last, Jr., First' with the      * first name full or abbreviated depending on parameter.      *      * @param abbr<CODE>true</CODE> - abbreviate first name,<CODE>false</CODE> -      *             do not abbreviate      * @return 'von Last, Jr., First' (if<CODE>abbr==false</CODE>) or      * 'von Last, Jr., F.' (if<CODE>abbr==true</CODE>)      */
DECL|method|getLastFirst (boolean abbr)
specifier|public
name|String
name|getLastFirst
parameter_list|(
name|boolean
name|abbr
parameter_list|)
block|{
name|StringBuilder
name|res
init|=
operator|new
name|StringBuilder
argument_list|(
name|getLastOnly
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|jrPart
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|jrPart
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|abbr
condition|)
block|{
if|if
condition|(
name|firstAbbr
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|firstAbbr
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|firstPart
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|firstPart
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|res
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns the author's name in form 'First von Last, Jr.' with the      * first name full or abbreviated depending on parameter.      *      * @param abbr<CODE>true</CODE> - abbreviate first name,<CODE>false</CODE> -      *             do not abbreviate      * @return 'First von Last, Jr.' (if<CODE>abbr==false</CODE>) or 'F.      * von Last, Jr.' (if<CODE>abbr==true</CODE>)      */
DECL|method|getFirstLast (boolean abbr)
specifier|public
name|String
name|getFirstLast
parameter_list|(
name|boolean
name|abbr
parameter_list|)
block|{
name|StringBuilder
name|res
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|abbr
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
name|firstAbbr
operator|==
literal|null
condition|?
literal|""
else|:
name|firstAbbr
operator|+
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|res
operator|.
name|append
argument_list|(
name|firstPart
operator|==
literal|null
condition|?
literal|""
else|:
name|firstPart
operator|+
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|jrPart
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|jrPart
argument_list|)
expr_stmt|;
block|}
return|return
name|res
operator|.
name|toString
argument_list|()
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
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"Author{"
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"firstPart='"
argument_list|)
operator|.
name|append
argument_list|(
name|firstPart
argument_list|)
operator|.
name|append
argument_list|(
literal|'\''
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", firstAbbr='"
argument_list|)
operator|.
name|append
argument_list|(
name|firstAbbr
argument_list|)
operator|.
name|append
argument_list|(
literal|'\''
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", vonPart='"
argument_list|)
operator|.
name|append
argument_list|(
name|vonPart
argument_list|)
operator|.
name|append
argument_list|(
literal|'\''
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", lastPart='"
argument_list|)
operator|.
name|append
argument_list|(
name|lastPart
argument_list|)
operator|.
name|append
argument_list|(
literal|'\''
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", jrPart='"
argument_list|)
operator|.
name|append
argument_list|(
name|jrPart
argument_list|)
operator|.
name|append
argument_list|(
literal|'\''
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns the name as "Last, Jr, F." omitting the von-part and removing      * starting braces.      *      * @return "Last, Jr, F." as described above or "" if all these parts      * are empty.      */
DECL|method|getNameForAlphabetization ()
specifier|public
name|String
name|getNameForAlphabetization
parameter_list|()
block|{
name|StringBuilder
name|res
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|lastPart
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
name|lastPart
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|jrPart
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
name|jrPart
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|firstAbbr
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|res
operator|.
name|append
argument_list|(
name|firstAbbr
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
operator|(
name|res
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|(
name|res
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'{'
operator|)
condition|)
block|{
name|res
operator|.
name|deleteCharAt
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
return|return
name|res
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit
