begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
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
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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

begin_comment
comment|/**  * Describe class<code>Util</code> here.  *  * @author<a href="mailto:"></a>  * @version 1.0  */
end_comment

begin_class
DECL|class|Util
specifier|public
class|class
name|Util
block|{
comment|// Colors are defined here.
DECL|field|fieldsCol
specifier|public
specifier|static
name|Color
name|fieldsCol
init|=
operator|new
name|Color
argument_list|(
literal|180
argument_list|,
literal|180
argument_list|,
literal|200
argument_list|)
decl_stmt|;
DECL|method|bool (boolean b)
specifier|public
specifier|static
name|void
name|bool
parameter_list|(
name|boolean
name|b
parameter_list|)
block|{
if|if
condition|(
name|b
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"true"
argument_list|)
expr_stmt|;
else|else
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"false"
argument_list|)
expr_stmt|;
block|}
DECL|method|pr (String s)
specifier|public
specifier|static
name|void
name|pr
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|pr_ (String s)
specifier|public
specifier|static
name|void
name|pr_
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|nCase (String s)
specifier|public
specifier|static
name|String
name|nCase
parameter_list|(
name|String
name|s
parameter_list|)
block|{
comment|// Make first character of String uppercase, and the
comment|// rest lowercase.
name|String
name|res
init|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|+
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
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
return|return
name|res
return|;
block|}
DECL|method|checkName (String s)
specifier|public
specifier|static
name|String
name|checkName
parameter_list|(
name|String
name|s
parameter_list|)
block|{
comment|// Append '.bib' to the string unless it ends with that.
name|String
name|extension
init|=
name|s
operator|.
name|substring
argument_list|(
name|s
operator|.
name|length
argument_list|()
operator|-
literal|4
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|extension
operator|.
name|equalsIgnoreCase
argument_list|(
literal|".bib"
argument_list|)
condition|)
return|return
name|s
operator|+
literal|".bib"
return|;
else|else
return|return
name|s
return|;
block|}
DECL|method|createId (BibtexEntryType type, BibtexDatabase database)
specifier|public
specifier|static
name|String
name|createId
parameter_list|(
name|BibtexEntryType
name|type
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|String
name|s
decl_stmt|;
do|do
block|{
name|s
operator|=
name|type
operator|.
name|getName
argument_list|()
operator|+
operator|(
operator|new
name|Integer
argument_list|(
call|(
name|int
call|)
argument_list|(
name|Math
operator|.
name|random
argument_list|()
operator|*
literal|10000
argument_list|)
argument_list|)
operator|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
do|while
condition|(
name|database
operator|.
name|getEntryById
argument_list|(
name|s
argument_list|)
operator|!=
literal|null
condition|)
do|;
return|return
name|s
return|;
block|}
comment|/**       * This method sets the location of a Dialog such that it is centered with      * regard to another window, but not outside the screen on the left and the top.      */
DECL|method|placeDialog (javax.swing.JDialog diag, java.awt.Container win)
specifier|public
specifier|static
name|void
name|placeDialog
parameter_list|(
name|javax
operator|.
name|swing
operator|.
name|JDialog
name|diag
parameter_list|,
name|java
operator|.
name|awt
operator|.
name|Container
name|win
parameter_list|)
block|{
name|Dimension
name|ds
init|=
name|diag
operator|.
name|getSize
argument_list|()
decl_stmt|,
name|df
init|=
name|win
operator|.
name|getSize
argument_list|()
decl_stmt|;
name|Point
name|pf
init|=
name|win
operator|.
name|getLocation
argument_list|()
decl_stmt|;
name|diag
operator|.
name|setLocation
argument_list|(
operator|new
name|Point
argument_list|(
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|pf
operator|.
name|x
operator|+
operator|(
name|df
operator|.
name|width
operator|-
name|ds
operator|.
name|width
operator|)
operator|/
literal|2
argument_list|)
argument_list|,
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|pf
operator|.
name|y
operator|+
operator|(
name|df
operator|.
name|height
operator|-
name|ds
operator|.
name|height
operator|)
operator|/
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * This method translates a field or string from Bibtex notation, with      * possibly text contained in " " or { }, and string references, concatenated       * by '#' characters, into Bibkeeper notation, where string references are      * enclosed in a pair of '#' characters.      */
DECL|method|parseField (String content)
specifier|public
specifier|static
name|String
name|parseField
parameter_list|(
name|String
name|content
parameter_list|)
block|{
if|if
condition|(
name|content
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|""
return|;
name|String
name|toSet
init|=
literal|""
decl_stmt|;
name|boolean
name|string
decl_stmt|;
comment|// Keeps track of whether the next item is
comment|// a reference to a string, or normal content. First we must
comment|// check which we begin with. We simply check if we can find
comment|// a '#' before either '"' or '{'.
name|int
name|hash
init|=
name|content
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|)
decl_stmt|,
name|wr1
init|=
name|content
operator|.
name|indexOf
argument_list|(
literal|'"'
argument_list|)
decl_stmt|,
name|wr2
init|=
name|content
operator|.
name|indexOf
argument_list|(
literal|'{'
argument_list|)
decl_stmt|,
name|end
init|=
name|content
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|hash
operator|==
operator|-
literal|1
condition|)
name|hash
operator|=
name|end
expr_stmt|;
if|if
condition|(
name|wr1
operator|==
operator|-
literal|1
condition|)
name|wr1
operator|=
name|end
expr_stmt|;
if|if
condition|(
name|wr2
operator|==
operator|-
literal|1
condition|)
name|wr2
operator|=
name|end
expr_stmt|;
if|if
condition|(
operator|(
operator|(
name|wr1
operator|==
name|end
operator|)
operator|&&
operator|(
name|wr2
operator|==
name|end
operator|)
operator|)
operator|||
operator|(
name|hash
operator|<
name|Math
operator|.
name|min
argument_list|(
name|wr1
argument_list|,
name|wr2
argument_list|)
operator|)
condition|)
name|string
operator|=
literal|true
expr_stmt|;
else|else
name|string
operator|=
literal|false
expr_stmt|;
comment|//System.out.println("FileLoader: "+content+" "+string+" "+hash+" "+wr1+" "+wr2);
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|content
argument_list|,
literal|"#"
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|// 'tok' splits at the '#' sign, and keeps delimiters
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|str
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
name|str
operator|.
name|equals
argument_list|(
literal|"#"
argument_list|)
condition|)
name|string
operator|=
operator|!
name|string
expr_stmt|;
else|else
block|{
if|if
condition|(
name|string
condition|)
block|{
comment|// This part should normally be a string, but if it's
comment|// a pure number, it is not.
name|String
name|s
init|=
name|shaveString
argument_list|(
name|str
argument_list|)
decl_stmt|;
try|try
block|{
name|Integer
operator|.
name|parseInt
argument_list|(
name|s
argument_list|)
expr_stmt|;
comment|// If there's no exception, it's a number.
name|toSet
operator|=
name|toSet
operator|+
name|s
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
name|toSet
operator|=
name|toSet
operator|+
literal|"#"
operator|+
name|shaveString
argument_list|(
name|str
argument_list|)
operator|+
literal|"#"
expr_stmt|;
block|}
block|}
else|else
name|toSet
operator|=
name|toSet
operator|+
name|shaveString
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|toSet
return|;
block|}
DECL|method|shaveString (String s)
specifier|public
specifier|static
name|String
name|shaveString
parameter_list|(
name|String
name|s
parameter_list|)
block|{
comment|// returns the string, after shaving off whitespace at the beginning
comment|// and end, and removing (at most) one pair of braces or " surrounding it.
if|if
condition|(
name|s
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|char
name|ch
init|=
literal|0
decl_stmt|,
name|ch2
init|=
literal|0
decl_stmt|;
name|int
name|beg
init|=
literal|0
decl_stmt|,
name|end
init|=
name|s
operator|.
name|length
argument_list|()
decl_stmt|;
comment|// We start out assuming nothing will be removed.
name|boolean
name|begok
init|=
literal|false
decl_stmt|,
name|endok
init|=
literal|false
decl_stmt|,
name|braok
init|=
literal|false
decl_stmt|;
while|while
condition|(
operator|!
name|begok
condition|)
block|{
if|if
condition|(
name|beg
operator|<
name|s
operator|.
name|length
argument_list|()
condition|)
block|{
name|ch
operator|=
name|s
operator|.
name|charAt
argument_list|(
name|beg
argument_list|)
expr_stmt|;
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|ch
argument_list|)
condition|)
name|beg
operator|++
expr_stmt|;
else|else
name|begok
operator|=
literal|true
expr_stmt|;
block|}
else|else
name|begok
operator|=
literal|true
expr_stmt|;
block|}
while|while
condition|(
operator|!
name|endok
condition|)
block|{
if|if
condition|(
name|end
operator|>
name|beg
operator|+
literal|1
condition|)
block|{
name|ch
operator|=
name|s
operator|.
name|charAt
argument_list|(
name|end
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|ch
argument_list|)
condition|)
name|end
operator|--
expr_stmt|;
else|else
name|endok
operator|=
literal|true
expr_stmt|;
block|}
else|else
name|endok
operator|=
literal|true
expr_stmt|;
block|}
comment|//	while (!braok) {
if|if
condition|(
name|end
operator|>
name|beg
operator|+
literal|1
condition|)
block|{
name|ch
operator|=
name|s
operator|.
name|charAt
argument_list|(
name|beg
argument_list|)
expr_stmt|;
name|ch2
operator|=
name|s
operator|.
name|charAt
argument_list|(
name|end
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
operator|(
name|ch
operator|==
literal|'{'
operator|)
operator|&&
operator|(
name|ch2
operator|==
literal|'}'
operator|)
operator|)
operator|||
operator|(
operator|(
name|ch
operator|==
literal|'"'
operator|)
operator|&&
operator|(
name|ch2
operator|==
literal|'"'
operator|)
operator|)
condition|)
block|{
name|beg
operator|++
expr_stmt|;
name|end
operator|--
expr_stmt|;
block|}
block|}
comment|//else
comment|//braok = true;
comment|//  } else
comment|//braok = true;
comment|//}
name|s
operator|=
name|s
operator|.
name|substring
argument_list|(
name|beg
argument_list|,
name|end
argument_list|)
expr_stmt|;
comment|//Util.pr(s);
return|return
name|s
return|;
block|}
comment|/**      * This method returns a String similar to the one passed in,      * except all whitespace and '#' characters are removed. These      * characters make a key unusable by bibtex.      */
DECL|method|checkLegalKey (String key)
specifier|public
specifier|static
name|String
name|checkLegalKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|StringBuffer
name|newKey
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|key
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|key
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|&
operator|(
name|c
operator|!=
literal|'#'
operator|)
condition|)
name|newKey
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|newKey
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|wrap2 (String in, int wrapAmount)
specifier|static
specifier|public
name|String
name|wrap2
parameter_list|(
name|String
name|in
parameter_list|,
name|int
name|wrapAmount
parameter_list|)
block|{
name|StringBuffer
name|out
init|=
operator|new
name|StringBuffer
argument_list|(
name|in
operator|.
name|replaceAll
argument_list|(
literal|"[ \\t\\n\\r]+"
argument_list|,
literal|" "
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|p
init|=
name|in
operator|.
name|length
argument_list|()
operator|-
name|wrapAmount
decl_stmt|;
while|while
condition|(
name|p
operator|>
literal|0
condition|)
block|{
name|p
operator|=
name|out
operator|.
name|lastIndexOf
argument_list|(
literal|" "
argument_list|,
name|p
argument_list|)
expr_stmt|;
if|if
condition|(
name|p
operator|<=
literal|0
operator|||
name|p
operator|<=
literal|20
condition|)
break|break;
else|else
block|{
name|out
operator|.
name|insert
argument_list|(
name|p
argument_list|,
literal|"\n\t"
argument_list|)
expr_stmt|;
block|}
name|p
operator|-=
name|wrapAmount
expr_stmt|;
block|}
return|return
name|out
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Returns a HashMap containing all words used in the database in the      * given field type. Characters in @param remove are not included.      * @param db a<code>BibtexDatabase</code> value      * @param field a<code>String</code> value      * @param remove a<code>String</code> value      * @return a<code>HashSet</code> value      */
DECL|method|findAllWordsInField (BibtexDatabase db, String field, String remove)
specifier|public
specifier|static
name|HashSet
name|findAllWordsInField
parameter_list|(
name|BibtexDatabase
name|db
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|remove
parameter_list|)
block|{
name|HashSet
name|res
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|StringTokenizer
name|tok
decl_stmt|;
name|Iterator
name|i
init|=
name|db
operator|.
name|getKeySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|BibtexEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|i
operator|.
name|next
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|Object
name|o
init|=
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
block|{
name|tok
operator|=
operator|new
name|StringTokenizer
argument_list|(
name|o
operator|.
name|toString
argument_list|()
argument_list|,
name|remove
argument_list|,
literal|false
argument_list|)
expr_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|res
operator|.
name|add
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|res
return|;
block|}
comment|/**      * Takes a String array and returns a string with the array's      * elements delimited by a certain String.      *      * @param strs String array to convert.      * @param delimiter String to use as delimiter.      * @return Delimited String.      */
DECL|method|stringArrayToDelimited (String[] strs, String delimiter)
specifier|public
specifier|static
name|String
name|stringArrayToDelimited
parameter_list|(
name|String
index|[]
name|strs
parameter_list|,
name|String
name|delimiter
parameter_list|)
block|{
if|if
condition|(
operator|(
name|strs
operator|==
literal|null
operator|)
operator|||
operator|(
name|strs
operator|.
name|length
operator|==
literal|0
operator|)
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|strs
operator|.
name|length
operator|==
literal|1
condition|)
return|return
name|strs
index|[
literal|0
index|]
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|strs
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|strs
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|delimiter
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|strs
index|[
name|strs
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * This methods assures all words in the given entry are recorded      * in their respective Completers, if any.      */
comment|/*    public static void updateCompletersForEntry(Hashtable autoCompleters, 					 BibtexEntry be) {        	for (Iterator j=autoCompleters.keySet().iterator(); 	     j.hasNext();) { 	    String field = (String)j.next(); 	    Completer comp = (Completer)autoCompleters.get(field); 	    comp.addAll(be.getField(field)); 	} 	}*/
block|}
end_class

end_unit

