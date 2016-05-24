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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_class
DECL|class|FileField
specifier|public
class|class
name|FileField
block|{
DECL|method|parse (String value)
specifier|public
specifier|static
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|parse
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
operator|(
name|value
operator|==
literal|null
operator|)
operator|||
name|value
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|entry
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|boolean
name|inXmlChar
init|=
literal|false
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
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
name|value
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
name|value
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|escaped
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
comment|// Check if we are entering an XML special character construct such
comment|// as "&#44;", because we need to know in order to ignore the semicolon.
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'&'
operator|)
operator|&&
operator|!
name|inXmlChar
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|value
operator|.
name|length
argument_list|()
operator|>
operator|(
name|i
operator|+
literal|1
operator|)
operator|)
operator|&&
operator|(
name|value
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'#'
operator|)
condition|)
block|{
name|inXmlChar
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
name|inXmlChar
operator|&&
operator|(
name|c
operator|==
literal|';'
operator|)
condition|)
block|{
comment|// Check if we are exiting an XML special character construct:
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|inXmlChar
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|':'
operator|)
condition|)
block|{
name|entry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|';'
operator|)
operator|&&
operator|!
name|inXmlChar
condition|)
block|{
name|entry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
name|files
operator|.
name|add
argument_list|(
name|convert
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|entry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|entry
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|files
operator|.
name|add
argument_list|(
name|convert
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|files
return|;
block|}
DECL|method|convert (List<String> entry)
specifier|private
specifier|static
name|ParsedFileField
name|convert
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|entry
parameter_list|)
block|{
comment|// ensure list has at least 3 fields
while|while
condition|(
name|entry
operator|.
name|size
argument_list|()
operator|<
literal|3
condition|)
block|{
name|entry
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
name|ParsedFileField
name|field
init|=
operator|new
name|ParsedFileField
argument_list|(
name|entry
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|entry
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|entry
operator|.
name|get
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
comment|// link is only mandatory field
if|if
condition|(
name|field
operator|.
name|getDescription
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
name|field
operator|.
name|getLink
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|field
operator|.
name|getFileType
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|field
operator|=
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
name|field
operator|.
name|getFileType
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|field
operator|.
name|getDescription
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
name|field
operator|.
name|getLink
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
name|field
operator|.
name|getFileType
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|field
operator|=
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
name|field
operator|.
name|getDescription
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|entry
operator|.
name|clear
argument_list|()
expr_stmt|;
return|return
name|field
return|;
block|}
DECL|method|getStringRepresentation (List<ParsedFileField> fields)
specifier|public
specifier|static
name|String
name|getStringRepresentation
parameter_list|(
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|fields
parameter_list|)
block|{
name|String
index|[]
index|[]
name|array
init|=
operator|new
name|String
index|[
name|fields
operator|.
name|size
argument_list|()
index|]
index|[]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ParsedFileField
name|entry
range|:
name|fields
control|)
block|{
name|array
index|[
name|i
index|]
operator|=
operator|new
name|String
index|[]
block|{
name|entry
operator|.
name|getDescription
argument_list|()
block|,
name|entry
operator|.
name|getLink
argument_list|()
block|,
name|entry
operator|.
name|getFileType
argument_list|()
block|}
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
return|return
name|encodeStringArray
argument_list|(
name|array
argument_list|)
return|;
block|}
DECL|method|getStringRepresentation (ParsedFileField field)
specifier|public
specifier|static
name|String
name|getStringRepresentation
parameter_list|(
name|ParsedFileField
name|field
parameter_list|)
block|{
return|return
name|getStringRepresentation
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|field
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Encodes a two-dimensional String array into a single string, using ':' and      * ';' as separators. The characters ':' and ';' are escaped with '\'.      * @param values The String array.      * @return The encoded String.      */
DECL|method|encodeStringArray (String[][] values)
specifier|public
specifier|static
name|String
name|encodeStringArray
parameter_list|(
name|String
index|[]
index|[]
name|values
parameter_list|)
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|values
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|FileField
operator|::
name|encodeStringArray
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|";"
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Encodes a String array into a single string, using ':' as separator.      * The characters ':' and ';' are escaped with '\'.      * @param entry The String array.      * @return The encoded String.      */
DECL|method|encodeStringArray (String[] entry)
specifier|private
specifier|static
name|String
name|encodeStringArray
parameter_list|(
name|String
index|[]
name|entry
parameter_list|)
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|FileField
operator|::
name|quote
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|":"
argument_list|)
argument_list|)
return|;
block|}
DECL|method|quote (String s)
specifier|public
specifier|static
name|String
name|quote
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
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
name|s
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
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
literal|';'
operator|)
operator|||
operator|(
name|c
operator|==
literal|':'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

