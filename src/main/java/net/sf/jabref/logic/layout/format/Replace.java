begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.layout.format
package|package
name|net
operator|.
name|sf
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
name|java
operator|.
name|util
operator|.
name|List
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
name|layout
operator|.
name|AbstractParamLayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Formatter that does regexp replacement.  *  * To use this formatter, a two-part argument must be given. The parts are  * separated by a comma. To indicate the comma character, use an escape  * sequence: \,  * For inserting newlines and tabs in arguments, use \n and \t, respectively.  *  * The first part is the regular expression to search for. Remember that any commma  * character must be preceded by a backslash, and consequently a literal backslash must  * be written as a pair of backslashes. A description of Java regular expressions can be  * found at:  *   http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html  *  * The second part is the text to replace all matches with.  *  * For instance:  *  \format[Replace(and,&)]{\author} :  *      will output the "author" field after replacing all occurences of "and"  *      by "&"  *  *  \format[Replace(\s,_)]{\author} :  *      will output the "author" field after replacing all whitespace  *      by underscores.  *  *  \format[Replace(\,,;)]{\author} :  *      will output the "author" field after replacing all commas by semicolons.  *  */
end_comment

begin_class
DECL|class|Replace
specifier|public
class|class
name|Replace
extends|extends
name|AbstractParamLayoutFormatter
block|{
DECL|field|regex
specifier|private
name|String
name|regex
decl_stmt|;
DECL|field|replaceWith
specifier|private
name|String
name|replaceWith
decl_stmt|;
annotation|@
name|Override
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|parts
init|=
name|AbstractParamLayoutFormatter
operator|.
name|parseArgument
argument_list|(
name|arg
argument_list|)
decl_stmt|;
if|if
condition|(
name|parts
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|)
block|{
return|return;
comment|// TODO: too few arguments. Print an error message here?
block|}
name|regex
operator|=
name|parts
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|replaceWith
operator|=
name|parts
operator|.
name|get
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
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
operator|(
name|fieldText
operator|==
literal|null
operator|)
operator|||
operator|(
name|regex
operator|==
literal|null
operator|)
condition|)
block|{
return|return
name|fieldText
return|;
comment|// TODO: argument missing or invalid. Print an error message here?
block|}
return|return
name|fieldText
operator|.
name|replaceAll
argument_list|(
name|regex
argument_list|,
name|replaceWith
argument_list|)
return|;
block|}
block|}
end_class

end_unit

