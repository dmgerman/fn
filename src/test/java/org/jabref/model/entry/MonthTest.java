begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|MonthTest
specifier|public
class|class
name|MonthTest
block|{
annotation|@
name|Test
DECL|method|parseCorrectlyByShortName ()
specifier|public
name|void
name|parseCorrectlyByShortName
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"jan"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"feb"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"mar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"apr"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"may"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"jun"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"jul"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"aug"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"sep"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"oct"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"nov"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"dec"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectlyByBibtexName ()
specifier|public
name|void
name|parseCorrectlyByBibtexName
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#jan#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#feb#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#mar#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#apr#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#may#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#jun#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#jul#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#aug#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#sep#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#oct#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#nov#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#dec#"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectlyByFullName ()
specifier|public
name|void
name|parseCorrectlyByFullName
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"January"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"February"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"March"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"April"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"May"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"June"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"July"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"August"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"September"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"October"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"November"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"December"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectlyByTwoDigitNumber ()
specifier|public
name|void
name|parseCorrectlyByTwoDigitNumber
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"01"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"02"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"03"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"04"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"05"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"06"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"07"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"08"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"09"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"10"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"11"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"12"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectlyByNumber ()
specifier|public
name|void
name|parseCorrectlyByNumber
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"1"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"3"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"4"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"5"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"6"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"7"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"8"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"9"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"10"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"11"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"12"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseReturnsEmptyOptionalForInvalidInput ()
specifier|public
name|void
name|parseReturnsEmptyOptionalForInvalidInput
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|";lkjasdf"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"3.2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"#test#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"8,"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseReturnsEmptyOptionalForEmptyInput ()
specifier|public
name|void
name|parseReturnsEmptyOptionalForEmptyInput
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectlyByShortNameGerman ()
specifier|public
name|void
name|parseCorrectlyByShortNameGerman
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Jan"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Feb"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"MÃ¤r"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Mae"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Apr"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Mai"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Jun"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Jul"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Aug"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Sep"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Okt"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Nov"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Dez"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectlyByFullNameGerman ()
specifier|public
name|void
name|parseCorrectlyByFullNameGerman
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Januar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Februar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"MÃ¤rz"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Maerz"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"April"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Mai"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Juni"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Juli"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"August"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"September"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Oktober"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"November"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"Dezember"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectlyByShortNameGermanLowercase ()
specifier|public
name|void
name|parseCorrectlyByShortNameGermanLowercase
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JANUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"jan"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|FEBRUARY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"feb"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"mÃ¤r"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"mae"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|APRIL
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"apr"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|MAY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"mai"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JUNE
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"jun"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|JULY
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"jul"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|AUGUST
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"aug"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|SEPTEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"sep"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|OCTOBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"okt"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|NOVEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"nov"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|Month
operator|.
name|DECEMBER
argument_list|)
argument_list|,
name|Month
operator|.
name|parse
argument_list|(
literal|"dez"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

