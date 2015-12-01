begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
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
name|logic
operator|.
name|formatter
operator|.
name|Formatter
import|;
end_import

begin_class
DECL|class|LatexFormatter
specifier|public
class|class
name|LatexFormatter
implements|implements
name|Formatter
block|{
DECL|method|LatexFormatter ()
specifier|public
name|LatexFormatter
parameter_list|()
block|{
comment|// TODO Auto-generated constructor stub
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Latex"
return|;
block|}
annotation|@
name|Override
DECL|method|format (String oldString)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|oldString
parameter_list|)
block|{
name|String
name|newValue
init|=
name|oldString
decl_stmt|;
comment|// Remove redundant $, {, and }, but not if the } is part of a command argument: \mbox{-}{GPS} should not be adjusted
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"$$"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"(?<!\\\\[\\p{Alpha}]{0,100}\\{[^\\}]{0,100})\\}([-/ ]?)\\{"
argument_list|,
literal|"$1"
argument_list|)
expr_stmt|;
comment|// Move numbers, +, -, /, and brackets into equations
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"(([^$]|\\\\\\$)*)\\$"
argument_list|,
literal|"$1@@"
argument_list|)
expr_stmt|;
comment|// Replace $, but not \$ with @@
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"([^@]*)@@([^@]*)@@"
argument_list|,
literal|"$1\\$$2@@"
argument_list|)
expr_stmt|;
comment|// Replace every other @@ with $
comment|//newValue = newValue.replaceAll("([0-9\\(\\.]+) \\$","\\$$1\\\\ "); // Move numbers followed by a space left of $ inside the equation, e.g., 0.35 $\mu$m
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"([0-9\\(\\.]+[ ]?[-+/]?[ ]?)\\$"
argument_list|,
literal|"\\$$1"
argument_list|)
expr_stmt|;
comment|// Move numbers, possibly with operators +, -, or /,  left of $ into the equation
name|newValue
operator|=
name|newValue
operator|.
name|replaceAll
argument_list|(
literal|"@@([ ]?[-+/]?[ ]?[0-9\\)\\.]+)"
argument_list|,
literal|" $1@@"
argument_list|)
expr_stmt|;
comment|// Move numbers right of @@ into the equation
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"@@"
argument_list|,
literal|"$"
argument_list|)
expr_stmt|;
comment|// Replace all @@ with $
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"  "
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
comment|// Clean up
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|"$$"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|newValue
operator|=
name|newValue
operator|.
name|replace
argument_list|(
literal|" )$"
argument_list|,
literal|")$"
argument_list|)
expr_stmt|;
return|return
name|newValue
return|;
block|}
block|}
end_class

end_unit

