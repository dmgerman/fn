begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
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
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Objects
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
name|formatter
operator|.
name|AbstractFormatter
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
name|l10n
operator|.
name|Localization
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
name|StringLengthComparator
import|;
end_import

begin_class
DECL|class|UnitsToLatexFormatter
specifier|public
class|class
name|UnitsToLatexFormatter
extends|extends
name|AbstractFormatter
block|{
DECL|field|UNIT_LIST
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|UNIT_LIST
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"A"
argument_list|,
comment|// Ampere
literal|"Ah"
argument_list|,
comment|// Ampere hours
literal|"B"
argument_list|,
comment|// Byte
literal|"Bq"
argument_list|,
comment|// Bequerel
literal|"C"
argument_list|,
comment|// Coulomb
literal|"F"
argument_list|,
comment|// Farad
literal|"Gy"
argument_list|,
comment|// Gray
literal|"H"
argument_list|,
comment|// Henry
literal|"Hz"
argument_list|,
comment|// Hertz
literal|"J"
argument_list|,
comment|// Joule
literal|"K"
argument_list|,
comment|// Kelvin
literal|"N"
argument_list|,
comment|// Newton
literal|"\\$\\\\Omega\\$"
argument_list|,
comment|// Ohm
literal|"Pa"
argument_list|,
comment|// Pascal
literal|"S"
argument_list|,
comment|// Siemens, Samples
literal|"Sa"
argument_list|,
comment|// Samples
literal|"Sv"
argument_list|,
comment|// Sv
literal|"T"
argument_list|,
comment|// Tesla
literal|"V"
argument_list|,
comment|// Volt
literal|"VA"
argument_list|,
comment|// Volt ampere
literal|"W"
argument_list|,
comment|// Watt
literal|"Wb"
argument_list|,
comment|// Weber
literal|"Wh"
argument_list|,
comment|// Watt hours
literal|"bar"
argument_list|,
comment|// bar
literal|"b"
argument_list|,
comment|// bit
literal|"cd"
argument_list|,
comment|// candela
literal|"dB"
argument_list|,
comment|// decibel
literal|"dBm"
argument_list|,
comment|// decibel
literal|"dBc"
argument_list|,
comment|//decibel
literal|"eV"
argument_list|,
comment|// electron volts
literal|"inch"
argument_list|,
comment|// inch
literal|"kat"
argument_list|,
comment|// katal
literal|"lm"
argument_list|,
comment|// lumen
literal|"lx"
argument_list|,
comment|// lux
literal|"m"
argument_list|,
comment|// meters
literal|"mol"
argument_list|,
comment|// mol
literal|"rad"
argument_list|,
comment|// radians
literal|"s"
argument_list|,
comment|// seconds
literal|"sr"
comment|// steradians
argument_list|)
decl_stmt|;
DECL|field|UNIT_PREFIX_LIST
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|UNIT_PREFIX_LIST
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"y"
argument_list|,
comment|// yocto
literal|"z"
argument_list|,
comment|// zepto
literal|"a"
argument_list|,
comment|// atto
literal|"f"
argument_list|,
comment|// femto
literal|"p"
argument_list|,
comment|// pico
literal|"n"
argument_list|,
comment|// nano
literal|"\\$\\\\mu\\$"
argument_list|,
comment|// micro
literal|"u"
argument_list|,
comment|// micro
literal|"m"
argument_list|,
comment|// milli
literal|"c"
argument_list|,
comment|// centi
literal|"d"
argument_list|,
comment|// deci
literal|""
argument_list|,
comment|// no prefix
literal|"da"
argument_list|,
comment|// deca
literal|"h"
argument_list|,
comment|// hekto
literal|"k"
argument_list|,
comment|// kilo
literal|"M"
argument_list|,
comment|// mega
literal|"G"
argument_list|,
comment|// giga
literal|"T"
argument_list|,
comment|// tera
literal|"P"
argument_list|,
comment|// peta
literal|"E"
argument_list|,
comment|// exa
literal|"Z"
argument_list|,
comment|// zetta
literal|"Y"
comment|// yotta
argument_list|)
decl_stmt|;
DECL|field|prefixUnitCombinations
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|prefixUnitCombinations
decl_stmt|;
DECL|method|UnitsToLatexFormatter ()
specifier|public
name|UnitsToLatexFormatter
parameter_list|()
block|{
name|prefixUnitCombinations
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|UnitsToLatexFormatter
operator|.
name|UNIT_LIST
operator|.
name|size
argument_list|()
operator|*
name|UnitsToLatexFormatter
operator|.
name|UNIT_PREFIX_LIST
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|unit
range|:
name|UnitsToLatexFormatter
operator|.
name|UNIT_LIST
control|)
block|{
for|for
control|(
name|String
name|prefix
range|:
name|UnitsToLatexFormatter
operator|.
name|UNIT_PREFIX_LIST
control|)
block|{
name|prefixUnitCombinations
operator|.
name|add
argument_list|(
name|prefix
operator|+
name|unit
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|prefixUnitCombinations
argument_list|,
operator|new
name|StringLengthComparator
argument_list|()
argument_list|)
expr_stmt|;
comment|// Sort based on string length
block|}
annotation|@
name|Override
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|text
argument_list|)
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|text
return|;
block|}
comment|// Replace the hyphen in 12-bit etc with a non-breaking hyphen, will also avoid bad casing of 12-Bit
name|String
name|result
init|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"([0-9,\\.]+)-([Bb][Ii][Tt])"
argument_list|,
literal|"$1\\\\mbox\\{-\\}$2"
argument_list|)
decl_stmt|;
comment|// Replace the space in 12 bit etc with a non-breaking space, will also avoid bad casing of 12 Bit
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"([0-9,\\.]+) ([Bb][Ii][Tt])"
argument_list|,
literal|"$1~$2"
argument_list|)
expr_stmt|;
comment|// For each word in the list
for|for
control|(
name|String
name|listOfWord
range|:
name|prefixUnitCombinations
control|)
block|{
comment|// Add {} if the character before is a space, -, /, (, [, or } or if it is at the start of the string but not if it is followed by a }
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"([0-9])("
operator|+
name|listOfWord
operator|+
literal|")"
argument_list|,
literal|"$1\\{$2\\}"
argument_list|)
expr_stmt|;
comment|// Only add brackets to keep case
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"([0-9])-("
operator|+
name|listOfWord
operator|+
literal|")"
argument_list|,
literal|"$1\\\\mbox\\{-\\}\\{$2\\}"
argument_list|)
expr_stmt|;
comment|// Replace hyphen with non-break hyphen
name|result
operator|=
name|result
operator|.
name|replaceAll
argument_list|(
literal|"([0-9]) ("
operator|+
name|listOfWord
operator|+
literal|")"
argument_list|,
literal|"$1~\\{$2\\}"
argument_list|)
expr_stmt|;
comment|// Replace space with a hard space
block|}
return|return
name|result
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Converts units to LaTeX formatting."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"1 Hz"
return|;
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Units to LaTeX"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"units_to_latex"
return|;
block|}
block|}
end_class

end_unit

