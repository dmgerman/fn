begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.errorconsole
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|errorconsole
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
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
name|logic
operator|.
name|util
operator|.
name|OS
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
name|exception
operator|.
name|ExceptionUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|logging
operator|.
name|log4j
operator|.
name|core
operator|.
name|LogEvent
import|;
end_import

begin_class
DECL|class|LogEventViewModel
specifier|public
class|class
name|LogEventViewModel
block|{
DECL|field|logEvent
specifier|private
name|LogEvent
name|logEvent
decl_stmt|;
DECL|method|LogEventViewModel (LogEvent logEvent)
specifier|public
name|LogEventViewModel
parameter_list|(
name|LogEvent
name|logEvent
parameter_list|)
block|{
name|this
operator|.
name|logEvent
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|logEvent
argument_list|)
expr_stmt|;
block|}
DECL|method|getDisplayText ()
specifier|public
name|String
name|getDisplayText
parameter_list|()
block|{
return|return
name|logEvent
operator|.
name|getMessage
argument_list|()
operator|.
name|getFormattedMessage
argument_list|()
return|;
block|}
DECL|method|getStyleClass ()
specifier|public
name|String
name|getStyleClass
parameter_list|()
block|{
switch|switch
condition|(
name|logEvent
operator|.
name|getLevel
argument_list|()
operator|.
name|getStandardLevel
argument_list|()
condition|)
block|{
case|case
name|ERROR
case|:
return|return
literal|"exception"
return|;
case|case
name|WARN
case|:
return|return
literal|"output"
return|;
case|case
name|INFO
case|:
default|default:
return|return
literal|"log"
return|;
block|}
block|}
DECL|method|getIcon ()
specifier|public
name|IconTheme
operator|.
name|JabRefIcon
name|getIcon
parameter_list|()
block|{
switch|switch
condition|(
name|logEvent
operator|.
name|getLevel
argument_list|()
operator|.
name|getStandardLevel
argument_list|()
condition|)
block|{
case|case
name|ERROR
case|:
return|return
operator|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|INTEGRITY_FAIL
operator|)
return|;
case|case
name|WARN
case|:
return|return
operator|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|INTEGRITY_WARN
operator|)
return|;
case|case
name|INFO
case|:
default|default:
return|return
operator|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|INTEGRITY_INFO
operator|)
return|;
block|}
block|}
DECL|method|getStackTrace ()
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getStackTrace
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|logEvent
operator|.
name|getMessage
argument_list|()
operator|.
name|getThrowable
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|ExceptionUtils
operator|::
name|getStackTrace
argument_list|)
return|;
block|}
DECL|method|getDetailedText ()
specifier|public
name|String
name|getDetailedText
parameter_list|()
block|{
return|return
name|getDisplayText
argument_list|()
operator|+
name|getStackTrace
argument_list|()
operator|.
name|map
argument_list|(
name|stacktrace
lambda|->
name|OS
operator|.
name|NEWLINE
operator|+
name|stacktrace
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
return|;
block|}
block|}
end_class

end_unit

