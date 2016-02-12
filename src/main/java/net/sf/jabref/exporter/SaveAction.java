begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|logic
operator|.
name|formatter
operator|.
name|Formatter
import|;
end_import

begin_comment
comment|/**  * Defines a mapping between a formatter and a field for which a save action can be applied  */
end_comment

begin_class
DECL|class|SaveAction
specifier|public
specifier|final
class|class
name|SaveAction
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|formatter
specifier|private
specifier|final
name|Formatter
name|formatter
decl_stmt|;
DECL|method|SaveAction (String fieldName, Formatter formatter)
specifier|public
name|SaveAction
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|Formatter
name|formatter
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|formatter
operator|=
name|formatter
expr_stmt|;
block|}
DECL|method|getFormatter ()
specifier|public
name|Formatter
name|getFormatter
parameter_list|()
block|{
return|return
name|formatter
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
name|SaveAction
name|that
init|=
operator|(
name|SaveAction
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|fieldName
operator|!=
literal|null
condition|?
operator|!
name|fieldName
operator|.
name|equals
argument_list|(
name|that
operator|.
name|fieldName
argument_list|)
else|:
name|that
operator|.
name|fieldName
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
return|return
operator|!
operator|(
name|formatter
operator|!=
literal|null
condition|?
operator|!
name|formatter
operator|.
name|equals
argument_list|(
name|that
operator|.
name|formatter
argument_list|)
else|:
name|that
operator|.
name|formatter
operator|!=
literal|null
operator|)
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
name|fieldName
operator|!=
literal|null
condition|?
name|fieldName
operator|.
name|hashCode
argument_list|()
else|:
literal|0
decl_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
operator|(
name|formatter
operator|!=
literal|null
condition|?
name|formatter
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
name|fieldName
operator|+
literal|": "
operator|+
name|formatter
operator|.
name|getKey
argument_list|()
return|;
block|}
block|}
end_class

end_unit

