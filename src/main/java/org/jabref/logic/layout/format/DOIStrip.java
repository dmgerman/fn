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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|DOI
import|;
end_import

begin_comment
comment|/**  * Will strip any prefixes from the Doi field, in order to output only the Doi number  */
end_comment

begin_class
DECL|class|DOIStrip
specifier|public
class|class
name|DOIStrip
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
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
return|return
name|DOI
operator|.
name|build
argument_list|(
name|fieldText
argument_list|)
operator|.
name|map
argument_list|(
name|DOI
operator|::
name|getDOI
argument_list|)
operator|.
name|orElse
argument_list|(
name|fieldText
argument_list|)
return|;
block|}
block|}
end_class

end_unit
