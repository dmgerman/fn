begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Function
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|DOI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|Eprint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|ISBN
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|Identifier
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|MathSciNetId
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|IdentifierParser
specifier|public
class|class
name|IdentifierParser
block|{
DECL|method|parse (String fieldName, String input)
specifier|public
specifier|static
name|Optional
argument_list|<
name|?
extends|extends
name|Identifier
argument_list|>
name|parse
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|input
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|input
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|Function
argument_list|<
name|String
argument_list|,
name|Optional
argument_list|<
name|?
extends|extends
name|Identifier
argument_list|>
argument_list|>
name|parser
init|=
name|getParserForField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
return|return
name|parser
operator|.
name|apply
argument_list|(
name|input
argument_list|)
return|;
block|}
DECL|method|getParserForField (String fieldName)
specifier|private
specifier|static
name|Function
argument_list|<
name|String
argument_list|,
name|Optional
argument_list|<
name|?
extends|extends
name|Identifier
argument_list|>
argument_list|>
name|getParserForField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
switch|switch
condition|(
name|fieldName
condition|)
block|{
case|case
name|FieldName
operator|.
name|DOI
case|:
return|return
name|DOI
operator|::
name|parse
return|;
case|case
name|FieldName
operator|.
name|ISBN
case|:
return|return
name|ISBN
operator|::
name|parse
return|;
case|case
name|FieldName
operator|.
name|EPRINT
case|:
return|return
name|Eprint
operator|::
name|build
return|;
case|case
name|FieldName
operator|.
name|MR_NUMBER
case|:
return|return
name|MathSciNetId
operator|::
name|parse
return|;
block|}
comment|// By default, just return empty optional
return|return
name|input
lambda|->
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit
