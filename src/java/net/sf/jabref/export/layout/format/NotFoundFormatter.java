begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
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
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * Formatter used to signal that a formatter hasn't been found. This can be  * used for graceful degradation if a layout uses an undefined format.  */
end_comment

begin_class
DECL|class|NotFoundFormatter
specifier|public
class|class
name|NotFoundFormatter
implements|implements
name|LayoutFormatter
block|{
DECL|field|notFound
specifier|private
name|String
name|notFound
decl_stmt|;
DECL|method|NotFoundFormatter (String notFound)
specifier|public
name|NotFoundFormatter
parameter_list|(
name|String
name|notFound
parameter_list|)
block|{
name|this
operator|.
name|notFound
operator|=
name|notFound
expr_stmt|;
block|}
DECL|method|getNotFound ()
specifier|public
name|String
name|getNotFound
parameter_list|()
block|{
return|return
name|notFound
return|;
block|}
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
return|return
literal|"["
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Formatter not found: %0"
argument_list|,
name|notFound
argument_list|)
operator|+
literal|"] "
operator|+
name|fieldText
return|;
block|}
block|}
end_class

end_unit

