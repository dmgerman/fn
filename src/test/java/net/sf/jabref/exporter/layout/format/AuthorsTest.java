begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|exporter
operator|.
name|layout
operator|.
name|ParamLayoutFormatter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|AuthorsTest
specifier|public
class|class
name|AuthorsTest
block|{
annotation|@
name|Test
DECL|method|testStandardUsage ()
specifier|public
name|void
name|testStandardUsage
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"B. C. Bruce, C. Manson and J. Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardUsageOne ()
specifier|public
name|void
name|testStandardUsageOne
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"fullname, LastFirst, Comma, Comma"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bruce, Bob Croydon, Jumper, Jolly"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardUsageTwo ()
specifier|public
name|void
name|testStandardUsageTwo
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"initials"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"B. C. Bruce and J. Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardUsageThree ()
specifier|public
name|void
name|testStandardUsageThree
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"fullname, LastFirst, Comma"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bruce, Bob Croydon, Manson, Charles and Jumper, Jolly"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardUsageFour ()
specifier|public
name|void
name|testStandardUsageFour
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"fullname, LastFirst, Comma, 2"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bruce, Bob Croydon et al."
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardUsageFive ()
specifier|public
name|void
name|testStandardUsageFive
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"fullname, LastFirst, Comma, 3"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bruce, Bob Croydon et al."
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Charles Manson and Jolly Jumper and Chuck Chuckles"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardUsageSix ()
specifier|public
name|void
name|testStandardUsageSix
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"fullname, LastFirst, Comma, 3, 2"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bruce, Bob Croydon, Manson, Charles et al."
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Charles Manson and Jolly Jumper and Chuck Chuckles"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSpecialEtAl ()
specifier|public
name|void
name|testSpecialEtAl
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"fullname, LastFirst, Comma, 3, etal= and a few more"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bruce, Bob Croydon and a few more"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Charles Manson and Jolly Jumper and Chuck Chuckles"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardUsageNull ()
specifier|public
name|void
name|testStandardUsageNull
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardOxford ()
specifier|public
name|void
name|testStandardOxford
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"Oxford"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"B. C. Bruce, C. Manson, and J. Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bob Croydon Bruce and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardOxfordFullName ()
specifier|public
name|void
name|testStandardOxfordFullName
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"FullName,Oxford"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bob Croydon Bruce, Charles Manson, and Jolly Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bruce, Bob Croydon and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardCommaFullName ()
specifier|public
name|void
name|testStandardCommaFullName
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"FullName,Comma,Comma"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bob Croydon Bruce, Charles Manson, Jolly Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bruce, Bob Croydon and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStandardAmpFullName ()
specifier|public
name|void
name|testStandardAmpFullName
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"FullName,Amp"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bob Croydon Bruce, Charles Manson& Jolly Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bruce, Bob Croydon and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testLastName ()
specifier|public
name|void
name|testLastName
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|Authors
argument_list|()
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"LastName"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Bruce, Manson and Jumper"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Bruce, Bob Croydon and Charles Manson and Jolly Jumper"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

