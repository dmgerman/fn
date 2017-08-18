begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|preferences
package|;
end_package

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|LocalDateTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_class
DECL|class|TimestampPreferences
specifier|public
class|class
name|TimestampPreferences
block|{
DECL|field|useTimestamps
specifier|private
specifier|final
name|boolean
name|useTimestamps
decl_stmt|;
DECL|field|useModifiedTimestamp
specifier|private
specifier|final
name|boolean
name|useModifiedTimestamp
decl_stmt|;
DECL|field|timestampField
specifier|private
specifier|final
name|String
name|timestampField
decl_stmt|;
DECL|field|timestampFormat
specifier|private
specifier|final
name|String
name|timestampFormat
decl_stmt|;
DECL|field|overwriteTimestamp
specifier|private
specifier|final
name|boolean
name|overwriteTimestamp
decl_stmt|;
DECL|method|TimestampPreferences (boolean useTimestamps, boolean useModifiedTimestamp, String timestampField, String timestampFormat, boolean overwriteTimestamp)
specifier|public
name|TimestampPreferences
parameter_list|(
name|boolean
name|useTimestamps
parameter_list|,
name|boolean
name|useModifiedTimestamp
parameter_list|,
name|String
name|timestampField
parameter_list|,
name|String
name|timestampFormat
parameter_list|,
name|boolean
name|overwriteTimestamp
parameter_list|)
block|{
name|this
operator|.
name|useTimestamps
operator|=
name|useTimestamps
expr_stmt|;
name|this
operator|.
name|useModifiedTimestamp
operator|=
name|useModifiedTimestamp
expr_stmt|;
name|this
operator|.
name|timestampField
operator|=
name|timestampField
expr_stmt|;
name|this
operator|.
name|timestampFormat
operator|=
name|timestampFormat
expr_stmt|;
name|this
operator|.
name|overwriteTimestamp
operator|=
name|overwriteTimestamp
expr_stmt|;
block|}
DECL|method|includeCreatedTimestamp ()
specifier|public
name|boolean
name|includeCreatedTimestamp
parameter_list|()
block|{
return|return
name|useTimestamps
return|;
block|}
DECL|method|includeModifiedTimestamp ()
specifier|public
name|boolean
name|includeModifiedTimestamp
parameter_list|()
block|{
return|return
name|useModifiedTimestamp
return|;
block|}
DECL|method|getTimestampField ()
specifier|public
name|String
name|getTimestampField
parameter_list|()
block|{
return|return
name|timestampField
return|;
block|}
DECL|method|getTimestampFormat ()
specifier|public
name|String
name|getTimestampFormat
parameter_list|()
block|{
return|return
name|timestampFormat
return|;
block|}
DECL|method|overwriteTimestamp ()
specifier|public
name|boolean
name|overwriteTimestamp
parameter_list|()
block|{
return|return
name|overwriteTimestamp
return|;
block|}
DECL|method|includeTimestamps ()
specifier|public
name|boolean
name|includeTimestamps
parameter_list|()
block|{
return|return
name|useTimestamps
operator|&&
name|useModifiedTimestamp
return|;
block|}
DECL|method|now ()
specifier|public
name|String
name|now
parameter_list|()
block|{
name|String
name|timeStampFormat
init|=
name|timestampFormat
decl_stmt|;
return|return
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
name|timeStampFormat
argument_list|)
operator|.
name|format
argument_list|(
name|LocalDateTime
operator|.
name|now
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit
