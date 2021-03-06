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
name|gui
operator|.
name|util
operator|.
name|DefaultTaskExecutor
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
name|Property
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
name|impl
operator|.
name|MutableLogEvent
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
name|plugins
operator|.
name|PluginBuilderFactory
import|;
end_import

begin_class
annotation|@
name|Plugin
argument_list|(
name|name
operator|=
literal|"GuiAppender"
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
DECL|class|GuiAppender
specifier|public
class|class
name|GuiAppender
extends|extends
name|AbstractAppender
block|{
DECL|method|GuiAppender (String name, Filter filter, boolean ignoreExceptions, Property[] properties)
specifier|private
name|GuiAppender
parameter_list|(
name|String
name|name
parameter_list|,
name|Filter
name|filter
parameter_list|,
name|boolean
name|ignoreExceptions
parameter_list|,
name|Property
index|[]
name|properties
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|filter
argument_list|,
literal|null
argument_list|,
name|ignoreExceptions
argument_list|,
name|properties
argument_list|)
expr_stmt|;
block|}
annotation|@
name|PluginBuilderFactory
DECL|method|newBuilder ()
specifier|public
specifier|static
parameter_list|<
name|B
extends|extends
name|GuiAppender
operator|.
name|Builder
argument_list|<
name|B
argument_list|>
parameter_list|>
name|B
name|newBuilder
parameter_list|()
block|{
return|return
operator|new
name|GuiAppender
operator|.
name|Builder
argument_list|<
name|B
argument_list|>
argument_list|()
operator|.
name|asBuilder
argument_list|()
return|;
block|}
comment|/**      * The log event will be forwarded to the {@link LogMessages} archive.      */
annotation|@
name|Override
DECL|method|append (LogEvent event)
specifier|public
name|void
name|append
parameter_list|(
name|LogEvent
name|event
parameter_list|)
block|{
comment|// We need to make a copy as instances of LogEvent are reused by log4j
name|MutableLogEvent
name|copy
init|=
operator|new
name|MutableLogEvent
argument_list|()
decl_stmt|;
name|copy
operator|.
name|initFrom
argument_list|(
name|event
argument_list|)
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|LogMessages
operator|.
name|getInstance
argument_list|()
operator|.
name|add
argument_list|(
name|copy
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|Builder
specifier|public
specifier|static
class|class
name|Builder
parameter_list|<
name|B
extends|extends
name|GuiAppender
operator|.
name|Builder
parameter_list|<
name|B
parameter_list|>
parameter_list|>
extends|extends
name|AbstractAppender
operator|.
name|Builder
argument_list|<
name|B
argument_list|>
implements|implements
name|org
operator|.
name|apache
operator|.
name|logging
operator|.
name|log4j
operator|.
name|plugins
operator|.
name|util
operator|.
name|Builder
argument_list|<
name|GuiAppender
argument_list|>
block|{
annotation|@
name|Override
DECL|method|build ()
specifier|public
name|GuiAppender
name|build
parameter_list|()
block|{
return|return
operator|new
name|GuiAppender
argument_list|(
name|this
operator|.
name|getName
argument_list|()
argument_list|,
name|this
operator|.
name|getFilter
argument_list|()
argument_list|,
name|this
operator|.
name|isIgnoreExceptions
argument_list|()
argument_list|,
name|this
operator|.
name|getPropertyArray
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

