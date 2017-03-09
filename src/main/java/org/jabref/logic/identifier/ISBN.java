begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.identifier
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|identifier
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|ISBN
specifier|public
class|class
name|ISBN
block|{
DECL|field|ISBN_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|ISBN_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^(\\d{9}[\\dxX]|\\d{13})$"
argument_list|)
decl_stmt|;
DECL|field|isbnString
specifier|private
specifier|final
name|String
name|isbnString
decl_stmt|;
DECL|method|ISBN (String isbnString)
specifier|public
name|ISBN
parameter_list|(
name|String
name|isbnString
parameter_list|)
block|{
name|this
operator|.
name|isbnString
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|isbnString
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|"-"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|isValidFormat ()
specifier|public
name|boolean
name|isValidFormat
parameter_list|()
block|{
name|Matcher
name|isbnMatcher
init|=
name|ISBN_PATTERN
operator|.
name|matcher
argument_list|(
name|isbnString
argument_list|)
decl_stmt|;
if|if
condition|(
name|isbnMatcher
operator|.
name|matches
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|isValidChecksum ()
specifier|public
name|boolean
name|isValidChecksum
parameter_list|()
block|{
name|boolean
name|valid
decl_stmt|;
if|if
condition|(
name|isbnString
operator|.
name|length
argument_list|()
operator|==
literal|10
condition|)
block|{
name|valid
operator|=
name|isbn10check
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// length is either 10 or 13 based on regexp so will be 13 here
name|valid
operator|=
name|isbn13check
argument_list|()
expr_stmt|;
block|}
return|return
name|valid
return|;
block|}
DECL|method|isIsbn10 ()
specifier|public
name|boolean
name|isIsbn10
parameter_list|()
block|{
return|return
name|isbn10check
argument_list|()
return|;
block|}
DECL|method|isIsbn13 ()
specifier|public
name|boolean
name|isIsbn13
parameter_list|()
block|{
return|return
name|isbn13check
argument_list|()
return|;
block|}
comment|// Check that the control digit is correct, see e.g. https://en.wikipedia.org/wiki/International_Standard_Book_Number#Check_digits
DECL|method|isbn10check ()
specifier|private
name|boolean
name|isbn10check
parameter_list|()
block|{
if|if
condition|(
name|isbnString
operator|.
name|length
argument_list|()
operator|!=
literal|10
condition|)
block|{
return|return
literal|false
return|;
block|}
name|int
name|sum
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|pos
init|=
literal|0
init|;
name|pos
operator|<=
literal|8
condition|;
name|pos
operator|++
control|)
block|{
name|sum
operator|+=
operator|(
name|isbnString
operator|.
name|charAt
argument_list|(
name|pos
argument_list|)
operator|-
literal|'0'
operator|)
operator|*
operator|(
operator|(
literal|10
operator|-
name|pos
operator|)
operator|)
expr_stmt|;
block|}
name|char
name|control
init|=
name|isbnString
operator|.
name|charAt
argument_list|(
literal|9
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|control
operator|==
literal|'x'
operator|)
operator|||
operator|(
name|control
operator|==
literal|'X'
operator|)
condition|)
block|{
name|control
operator|=
literal|'9'
operator|+
literal|1
expr_stmt|;
block|}
name|sum
operator|+=
operator|(
name|control
operator|-
literal|'0'
operator|)
expr_stmt|;
return|return
operator|(
name|sum
operator|%
literal|11
operator|)
operator|==
literal|0
return|;
block|}
comment|// Check that the control digit is correct, see e.g. https://en.wikipedia.org/wiki/International_Standard_Book_Number#Check_digits
DECL|method|isbn13check ()
specifier|private
name|boolean
name|isbn13check
parameter_list|()
block|{
if|if
condition|(
name|isbnString
operator|.
name|length
argument_list|()
operator|!=
literal|13
condition|)
block|{
return|return
literal|false
return|;
block|}
name|int
name|sum
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|pos
init|=
literal|0
init|;
name|pos
operator|<=
literal|12
condition|;
name|pos
operator|++
control|)
block|{
name|sum
operator|+=
operator|(
name|isbnString
operator|.
name|charAt
argument_list|(
name|pos
argument_list|)
operator|-
literal|'0'
operator|)
operator|*
operator|(
operator|(
name|pos
operator|%
literal|2
operator|)
operator|==
literal|0
condition|?
literal|1
else|:
literal|3
operator|)
expr_stmt|;
block|}
return|return
operator|(
name|sum
operator|%
literal|10
operator|)
operator|==
literal|0
return|;
block|}
DECL|method|isValid ()
specifier|public
name|boolean
name|isValid
parameter_list|()
block|{
return|return
name|isValidFormat
argument_list|()
operator|&&
name|isValidChecksum
argument_list|()
return|;
block|}
block|}
end_class

end_unit
