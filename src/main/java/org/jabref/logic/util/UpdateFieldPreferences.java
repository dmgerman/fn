begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_class
DECL|class|UpdateFieldPreferences
specifier|public
class|class
name|UpdateFieldPreferences
block|{
DECL|field|useOwner
specifier|private
specifier|final
name|boolean
name|useOwner
decl_stmt|;
DECL|field|useTimeStamp
specifier|private
specifier|final
name|boolean
name|useTimeStamp
decl_stmt|;
DECL|field|overwriteOwner
specifier|private
specifier|final
name|boolean
name|overwriteOwner
decl_stmt|;
DECL|field|overwriteTimeStamp
specifier|private
specifier|final
name|boolean
name|overwriteTimeStamp
decl_stmt|;
DECL|field|timeStampField
specifier|private
specifier|final
name|String
name|timeStampField
decl_stmt|;
DECL|field|timeStampFormat
specifier|private
specifier|final
name|String
name|timeStampFormat
decl_stmt|;
DECL|field|defaultOwner
specifier|private
specifier|final
name|String
name|defaultOwner
decl_stmt|;
DECL|method|UpdateFieldPreferences (boolean useOwner, boolean overwriteOwner, String defaultOwner, boolean useTimeStamp, boolean overwriteTimeStamp, String timeStampField, String timeStampFormat)
specifier|public
name|UpdateFieldPreferences
parameter_list|(
name|boolean
name|useOwner
parameter_list|,
name|boolean
name|overwriteOwner
parameter_list|,
name|String
name|defaultOwner
parameter_list|,
name|boolean
name|useTimeStamp
parameter_list|,
name|boolean
name|overwriteTimeStamp
parameter_list|,
name|String
name|timeStampField
parameter_list|,
name|String
name|timeStampFormat
parameter_list|)
block|{
name|this
operator|.
name|useOwner
operator|=
name|useOwner
expr_stmt|;
name|this
operator|.
name|overwriteOwner
operator|=
name|overwriteOwner
expr_stmt|;
name|this
operator|.
name|defaultOwner
operator|=
name|defaultOwner
expr_stmt|;
name|this
operator|.
name|useTimeStamp
operator|=
name|useTimeStamp
expr_stmt|;
name|this
operator|.
name|overwriteTimeStamp
operator|=
name|overwriteTimeStamp
expr_stmt|;
name|this
operator|.
name|timeStampField
operator|=
name|timeStampField
expr_stmt|;
name|this
operator|.
name|timeStampFormat
operator|=
name|timeStampFormat
expr_stmt|;
block|}
DECL|method|isUseOwner ()
specifier|public
name|boolean
name|isUseOwner
parameter_list|()
block|{
return|return
name|useOwner
return|;
block|}
DECL|method|isUseTimeStamp ()
specifier|public
name|boolean
name|isUseTimeStamp
parameter_list|()
block|{
return|return
name|useTimeStamp
return|;
block|}
DECL|method|getTimeStampField ()
specifier|public
name|String
name|getTimeStampField
parameter_list|()
block|{
return|return
name|timeStampField
return|;
block|}
DECL|method|getDefaultOwner ()
specifier|public
name|String
name|getDefaultOwner
parameter_list|()
block|{
return|return
name|defaultOwner
return|;
block|}
DECL|method|getTimeStampFormat ()
specifier|public
name|String
name|getTimeStampFormat
parameter_list|()
block|{
return|return
name|timeStampFormat
return|;
block|}
DECL|method|isOverwriteOwner ()
specifier|public
name|boolean
name|isOverwriteOwner
parameter_list|()
block|{
return|return
name|overwriteOwner
return|;
block|}
DECL|method|isOverwriteTimeStamp ()
specifier|public
name|boolean
name|isOverwriteTimeStamp
parameter_list|()
block|{
return|return
name|overwriteTimeStamp
return|;
block|}
block|}
end_class

end_unit

