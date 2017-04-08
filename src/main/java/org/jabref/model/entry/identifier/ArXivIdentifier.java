begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.identifier
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
name|Optional
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_class
DECL|class|ArXivIdentifier
specifier|public
class|class
name|ArXivIdentifier
implements|implements
name|Identifier
block|{
DECL|field|identifier
specifier|private
specifier|final
name|String
name|identifier
decl_stmt|;
DECL|method|ArXivIdentifier (String identifier)
name|ArXivIdentifier
parameter_list|(
name|String
name|identifier
parameter_list|)
block|{
name|this
operator|.
name|identifier
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|identifier
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
DECL|method|parse (String value)
specifier|public
specifier|static
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parse
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|String
name|identifier
init|=
name|value
operator|.
name|replaceAll
argument_list|(
literal|"(?i)arxiv:"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
name|identifier
argument_list|)
argument_list|)
return|;
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
return|return
literal|true
return|;
if|if
condition|(
name|o
operator|==
literal|null
operator|||
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|ArXivIdentifier
name|that
init|=
operator|(
name|ArXivIdentifier
operator|)
name|o
decl_stmt|;
return|return
name|identifier
operator|.
name|equals
argument_list|(
name|that
operator|.
name|identifier
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
name|identifier
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getDefaultField ()
specifier|public
name|String
name|getDefaultField
parameter_list|()
block|{
return|return
name|FieldName
operator|.
name|EPRINT
return|;
block|}
annotation|@
name|Override
DECL|method|getNormalized ()
specifier|public
name|String
name|getNormalized
parameter_list|()
block|{
return|return
name|identifier
return|;
block|}
block|}
end_class

end_unit

