begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.integrity
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
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
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang3
operator|.
name|builder
operator|.
name|EqualsBuilder
import|;
end_import

begin_class
DECL|class|IntegrityMessage
specifier|public
specifier|final
class|class
name|IntegrityMessage
implements|implements
name|Cloneable
block|{
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|message
specifier|private
specifier|final
name|String
name|message
decl_stmt|;
DECL|method|IntegrityMessage (String message, BibEntry entry, String fieldName)
specifier|public
name|IntegrityMessage
parameter_list|(
name|String
name|message
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|this
operator|.
name|message
operator|=
name|message
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
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
literal|"["
operator|+
name|getEntry
argument_list|()
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
operator|+
literal|"] in "
operator|+
name|getFieldName
argument_list|()
operator|+
literal|": "
operator|+
name|getMessage
argument_list|()
return|;
block|}
DECL|method|getMessage ()
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|message
return|;
block|}
DECL|method|getEntry ()
specifier|public
name|BibEntry
name|getEntry
parameter_list|()
block|{
return|return
name|entry
return|;
block|}
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
annotation|@
name|Override
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
block|{
return|return
operator|new
name|IntegrityMessage
argument_list|(
name|message
argument_list|,
name|entry
argument_list|,
name|fieldName
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|obj
operator|==
name|this
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|obj
operator|.
name|getClass
argument_list|()
operator|!=
name|getClass
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
name|IntegrityMessage
name|that
init|=
operator|(
name|IntegrityMessage
operator|)
name|obj
decl_stmt|;
return|return
operator|new
name|EqualsBuilder
argument_list|()
operator|.
name|append
argument_list|(
name|entry
argument_list|,
name|that
operator|.
name|entry
argument_list|)
operator|.
name|append
argument_list|(
name|fieldName
argument_list|,
name|that
operator|.
name|fieldName
argument_list|)
operator|.
name|append
argument_list|(
name|message
argument_list|,
name|that
operator|.
name|message
argument_list|)
operator|.
name|isEquals
argument_list|()
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
name|entry
argument_list|,
name|fieldName
argument_list|,
name|message
argument_list|)
return|;
block|}
block|}
end_class

end_unit

