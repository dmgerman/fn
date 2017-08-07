begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.logging
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|logging
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|logging
operator|.
name|LogMessages
import|;
end_import

begin_import
import|import
name|com
operator|.
name|microsoft
operator|.
name|applicationinsights
operator|.
name|log4j
operator|.
name|v2
operator|.
name|internal
operator|.
name|ApplicationInsightsLogEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|microsoft
operator|.
name|applicationinsights
operator|.
name|telemetry
operator|.
name|ExceptionTelemetry
import|;
end_import

begin_import
import|import
name|com
operator|.
name|microsoft
operator|.
name|applicationinsights
operator|.
name|telemetry
operator|.
name|Telemetry
import|;
end_import

begin_import
import|import
name|com
operator|.
name|microsoft
operator|.
name|applicationinsights
operator|.
name|telemetry
operator|.
name|TraceTelemetry
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
name|Filter
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
name|appender
operator|.
name|AbstractAppender
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
name|config
operator|.
name|plugins
operator|.
name|Plugin
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
name|config
operator|.
name|plugins
operator|.
name|PluginAttribute
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
name|config
operator|.
name|plugins
operator|.
name|PluginElement
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
name|config
operator|.
name|plugins
operator|.
name|PluginFactory
import|;
end_import

begin_class
annotation|@
name|Plugin
argument_list|(
name|name
operator|=
literal|"OurApplicationInsightsAppender"
argument_list|,
name|category
operator|=
literal|"Core"
argument_list|,
name|elementType
operator|=
literal|"appender"
argument_list|,
name|printObject
operator|=
literal|true
argument_list|)
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
comment|// class is indirectly constructed by log4j
DECL|class|ApplicationInsightsAppender
specifier|public
class|class
name|ApplicationInsightsAppender
extends|extends
name|AbstractAppender
block|{
DECL|method|ApplicationInsightsAppender (String name, Filter filter)
specifier|private
name|ApplicationInsightsAppender
parameter_list|(
name|String
name|name
parameter_list|,
name|Filter
name|filter
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|filter
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|PluginFactory
DECL|method|createAppender (@luginAttributeR) String name, @PluginElement(R) Filter filter)
specifier|public
specifier|static
name|ApplicationInsightsAppender
name|createAppender
parameter_list|(
annotation|@
name|PluginAttribute
argument_list|(
literal|"name"
argument_list|)
name|String
name|name
parameter_list|,
annotation|@
name|PluginElement
argument_list|(
literal|"Filters"
argument_list|)
name|Filter
name|filter
parameter_list|)
block|{
return|return
operator|new
name|ApplicationInsightsAppender
argument_list|(
name|name
argument_list|,
name|filter
argument_list|)
return|;
block|}
comment|/**      * The log event will be forwarded to the {@link LogMessages} archive.      */
annotation|@
name|Override
DECL|method|append (LogEvent rawEvent)
specifier|public
name|void
name|append
parameter_list|(
name|LogEvent
name|rawEvent
parameter_list|)
block|{
name|ApplicationInsightsLogEvent
name|event
init|=
operator|new
name|ApplicationInsightsLogEvent
argument_list|(
name|rawEvent
argument_list|)
decl_stmt|;
name|Telemetry
name|telemetry
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|isException
argument_list|()
condition|)
block|{
name|ExceptionTelemetry
name|exceptionTelemetry
init|=
operator|new
name|ExceptionTelemetry
argument_list|(
name|event
operator|.
name|getException
argument_list|()
argument_list|)
decl_stmt|;
name|exceptionTelemetry
operator|.
name|setSeverityLevel
argument_list|(
name|event
operator|.
name|getNormalizedSeverityLevel
argument_list|()
argument_list|)
expr_stmt|;
name|telemetry
operator|=
name|exceptionTelemetry
expr_stmt|;
block|}
else|else
block|{
name|TraceTelemetry
name|traceTelemetry
init|=
operator|new
name|TraceTelemetry
argument_list|(
name|event
operator|.
name|getMessage
argument_list|()
argument_list|)
decl_stmt|;
name|traceTelemetry
operator|.
name|setSeverityLevel
argument_list|(
name|event
operator|.
name|getNormalizedSeverityLevel
argument_list|()
argument_list|)
expr_stmt|;
name|telemetry
operator|=
name|traceTelemetry
expr_stmt|;
block|}
name|telemetry
operator|.
name|getContext
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|putAll
argument_list|(
name|event
operator|.
name|getCustomParameters
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|getTelemetryClient
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|client
lambda|->
name|client
operator|.
name|track
argument_list|(
name|telemetry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

