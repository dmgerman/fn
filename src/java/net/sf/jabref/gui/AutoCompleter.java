begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|AuthorList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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

begin_comment
comment|/**  * Created by Morten O. Alver, 16 Feb. 2007  */
end_comment

begin_class
DECL|class|AutoCompleter
specifier|public
class|class
name|AutoCompleter
block|{
DECL|field|SHORTEST_WORD
specifier|final
name|int
name|SHORTEST_WORD
init|=
literal|4
decl_stmt|,
DECL|field|SHORTEST_TO_COMPLETE
name|SHORTEST_TO_COMPLETE
init|=
literal|2
decl_stmt|;
DECL|field|words
specifier|private
name|TreeSet
name|words
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
DECL|field|hm
specifier|private
name|HashMap
name|hm
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|nameField
specifier|private
name|boolean
name|nameField
init|=
literal|false
decl_stmt|,
comment|// Attempt to store entire names?
DECL|field|entireField
name|entireField
init|=
literal|false
decl_stmt|;
comment|// Set to true if the entire field should be stored
comment|// suitable e.g. for journal or publisher fields.
DECL|method|AutoCompleter (String fieldName)
specifier|public
name|AutoCompleter
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
operator|||
name|fieldName
operator|.
name|equals
argument_list|(
literal|"editor"
argument_list|)
condition|)
name|nameField
operator|=
literal|true
expr_stmt|;
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
literal|"journal"
argument_list|)
operator|||
name|fieldName
operator|.
name|equals
argument_list|(
literal|"publisher"
argument_list|)
condition|)
name|entireField
operator|=
literal|true
expr_stmt|;
block|}
DECL|method|addWord (String word)
specifier|public
name|void
name|addWord
parameter_list|(
name|String
name|word
parameter_list|)
block|{
if|if
condition|(
name|word
operator|.
name|length
argument_list|()
operator|>=
name|SHORTEST_WORD
condition|)
name|words
operator|.
name|add
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
DECL|method|addAll (Object s)
specifier|public
name|void
name|addAll
parameter_list|(
name|Object
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|nameField
condition|)
block|{
name|AuthorList
name|list
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|s
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|processed
init|=
name|list
operator|.
name|getAuthorsLastFirstAnds
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|String
index|[]
name|names
init|=
name|processed
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
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
name|names
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|name
init|=
name|names
index|[
name|i
index|]
decl_stmt|;
name|addWord
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|entireField
condition|)
block|{
name|addWord
argument_list|(
name|s
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|s
operator|.
name|toString
argument_list|()
argument_list|,
literal|" .,\n"
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|word
init|=
name|tok
operator|.
name|nextToken
argument_list|()
decl_stmt|;
comment|//Util.pr(word);
name|addWord
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|complete (String s)
specifier|public
name|Object
index|[]
name|complete
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|.
name|length
argument_list|()
operator|<
name|SHORTEST_TO_COMPLETE
condition|)
return|return
literal|null
return|;
name|char
name|lastChar
init|=
name|s
operator|.
name|charAt
argument_list|(
name|s
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
name|String
name|ender
init|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|s
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|+
name|Character
operator|.
name|toString
argument_list|(
call|(
name|char
call|)
argument_list|(
name|lastChar
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|words
operator|.
name|subSet
argument_list|(
name|s
argument_list|,
name|ender
argument_list|)
operator|.
name|toArray
argument_list|()
return|;
block|}
block|}
end_class

end_unit

