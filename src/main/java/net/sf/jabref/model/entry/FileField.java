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
name|Objects
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
DECL|field|NULL_OBJECT
specifier|private
specifier|static
specifier|final
name|FileField
operator|.
name|ParsedFileField
name|NULL_OBJECT
init|=
operator|new
name|FileField
operator|.
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|)
decl_stmt|;
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
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
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
name|entry
lambda|->
name|encodeStringArray
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
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
name|String
operator|.
name|join
argument_list|(
literal|":"
argument_list|,
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
name|string
lambda|->
name|encodeString
argument_list|(
name|string
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|method|encodeString (String s)
specifier|private
specifier|static
name|String
name|encodeString
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
DECL|class|ParsedFileField
specifier|public
specifier|static
class|class
name|ParsedFileField
block|{
DECL|field|description
specifier|public
specifier|final
name|String
name|description
decl_stmt|;
DECL|field|link
specifier|public
specifier|final
name|String
name|link
decl_stmt|;
DECL|field|fileType
specifier|public
specifier|final
name|String
name|fileType
decl_stmt|;
DECL|method|ParsedFileField (String description, String link, String fileType)
specifier|public
name|ParsedFileField
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|link
parameter_list|,
name|String
name|fileType
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|this
operator|.
name|link
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|link
argument_list|)
expr_stmt|;
name|this
operator|.
name|fileType
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fileType
argument_list|)
expr_stmt|;
block|}
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
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|this
operator|.
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|FileField
operator|.
name|ParsedFileField
name|that
init|=
operator|(
name|FileField
operator|.
name|ParsedFileField
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|description
operator|!=
literal|null
condition|?
operator|!
name|this
operator|.
name|description
operator|.
name|equals
argument_list|(
name|that
operator|.
name|description
argument_list|)
else|:
name|that
operator|.
name|description
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|this
operator|.
name|link
operator|!=
literal|null
condition|?
operator|!
name|this
operator|.
name|link
operator|.
name|equals
argument_list|(
name|that
operator|.
name|link
argument_list|)
else|:
name|that
operator|.
name|link
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|this
operator|.
name|fileType
operator|!=
literal|null
condition|?
name|this
operator|.
name|fileType
operator|.
name|equals
argument_list|(
name|that
operator|.
name|fileType
argument_list|)
else|:
name|that
operator|.
name|fileType
operator|==
literal|null
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
name|int
name|result
init|=
name|this
operator|.
name|description
operator|!=
literal|null
condition|?
name|this
operator|.
name|description
operator|.
name|hashCode
argument_list|()
else|:
literal|0
decl_stmt|;
name|result
operator|=
operator|(
literal|31
operator|*
name|result
operator|)
operator|+
operator|(
name|this
operator|.
name|link
operator|!=
literal|null
condition|?
name|this
operator|.
name|link
operator|.
name|hashCode
argument_list|()
else|:
literal|0
operator|)
expr_stmt|;
name|result
operator|=
operator|(
literal|31
operator|*
name|result
operator|)
operator|+
operator|(
name|this
operator|.
name|fileType
operator|!=
literal|null
condition|?
name|this
operator|.
name|fileType
operator|.
name|hashCode
argument_list|()
else|:
literal|0
operator|)
expr_stmt|;
return|return
name|result
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
literal|"ParsedFileField{"
operator|+
literal|"description='"
operator|+
name|description
operator|+
literal|'\''
operator|+
literal|", link='"
operator|+
name|link
operator|+
literal|'\''
operator|+
literal|", fileType='"
operator|+
name|fileType
operator|+
literal|'\''
operator|+
literal|'}'
return|;
block|}
DECL|method|isEmpty ()
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|NULL_OBJECT
operator|.
name|equals
argument_list|(
name|this
argument_list|)
return|;
block|}
block|}
DECL|method|parse (String value)
specifier|public
specifier|static
name|List
argument_list|<
name|FileField
operator|.
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
name|FileField
operator|.
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
name|FileField
operator|.
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
name|FileField
operator|.
name|ParsedFileField
name|field
init|=
operator|new
name|FileField
operator|.
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
name|description
operator|.
name|isEmpty
argument_list|()
operator|&&
name|field
operator|.
name|link
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|field
operator|.
name|fileType
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
name|fileType
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
name|description
operator|.
name|isEmpty
argument_list|()
operator|&&
name|field
operator|.
name|link
operator|.
name|isEmpty
argument_list|()
operator|&&
name|field
operator|.
name|fileType
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
name|description
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
name|String
name|type
init|=
name|entry
operator|.
name|fileType
operator|!=
literal|null
condition|?
name|entry
operator|.
name|fileType
else|:
literal|""
decl_stmt|;
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
name|description
block|,
name|entry
operator|.
name|link
block|,
name|type
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
block|}
end_class

end_unit

