begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.cli
package|package
name|org
operator|.
name|jabref
operator|.
name|cli
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeMap
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
name|strings
operator|.
name|HTMLUnicodeConversionMaps
import|;
end_import

begin_class
DECL|class|GenerateCharacterTable
specifier|public
class|class
name|GenerateCharacterTable
block|{
DECL|method|GenerateCharacterTable ()
specifier|private
name|GenerateCharacterTable
parameter_list|()
block|{     }
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|Map
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|characterMap
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|(
name|HTMLUnicodeConversionMaps
operator|.
name|NUMERICAL_LATEX_CONVERSION_MAP
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\documentclass[10pt, a4paper]{article}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage[T5,T1]{fontenc}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{amssymb}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{amsmath}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{txfonts}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{xfrac}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{combelow}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{textcomp}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{mathspec}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{fontspec}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage[a4paper,margin=1cm]{geometry}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{supertabular}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\usepackage{mathabx}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\fontspec{Cambria}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\DeclareTextSymbolDefault{\\OHORN}{T5}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\DeclareTextSymbolDefault{\\UHORN}{T5}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\DeclareTextSymbolDefault{\\ohorn}{T5}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\DeclareTextSymbolDefault{\\uhorn}{T5}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\begin{document}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\twocolumn"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\begin{supertabular}{c|c|c|c|c}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"No.& Uni& Symb& \\LaTeX& Code \\\\ \n \\hline"
argument_list|)
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|character
range|:
name|characterMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|character
operator|.
name|getKey
argument_list|()
operator|+
literal|"& "
operator|+
operator|(
operator|(
name|character
operator|.
name|getKey
argument_list|()
operator|>
literal|128
operator|)
condition|?
name|String
operator|.
name|valueOf
argument_list|(
name|Character
operator|.
name|toChars
argument_list|(
name|character
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|)
else|:
literal|""
operator|)
operator|+
literal|"& \\symbol{"
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|character
operator|.
name|getKey
argument_list|()
argument_list|)
operator|+
literal|"}& "
operator|+
name|character
operator|.
name|getValue
argument_list|()
operator|+
literal|"& \\verbÂ¤"
operator|+
name|character
operator|.
name|getValue
argument_list|()
operator|+
literal|"Â¤ \\\\"
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\end{supertabular}"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\\end{document}"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

