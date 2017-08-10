begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_class
DECL|class|AuthorAndToSemicolonReplacer
specifier|public
class|class
name|AuthorAndToSemicolonReplacer
implements|implements
name|LayoutFormatter
block|{
annotation|@
name|Override
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
name|fieldText
operator|.
name|replaceAll
argument_list|(
literal|" and "
argument_list|,
literal|"; "
argument_list|)
return|;
block|}
block|}
end_class

end_unit

