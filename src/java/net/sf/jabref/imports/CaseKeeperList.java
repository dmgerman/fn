begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_comment
comment|/**  * Container class for lists with keywords where the case should be kept independent of bibstyle  *   */
end_comment

begin_class
DECL|class|CaseKeeperList
specifier|public
class|class
name|CaseKeeperList
block|{
comment|// Common words in IEEE Xplore that should always be in the given case
DECL|field|wordListIEEEXplore
specifier|public
name|String
index|[]
name|wordListIEEEXplore
init|=
operator|new
name|String
index|[]
block|{
literal|"1-D"
block|,
literal|"1D"
block|,
literal|"2-D"
block|,
literal|"2D"
block|,
literal|"3-D"
block|,
literal|"3D"
block|,
literal|"3G"
block|,
literal|"3GPP"
block|,
literal|"4-D"
block|,
literal|"4D"
block|,
literal|"4G"
block|,
literal|"4H"
block|,
literal|"6H"
block|,
literal|"A/D"
block|,
literal|"ACM"
block|,
literal|"AC"
block|,
literal|"ADC"
block|,
literal|"ADI"
block|,
literal|"ADSL"
block|,
literal|"ADP"
block|,
literal|"ADPLL"
block|,
literal|"AES"
block|,
literal|"AFE"
block|,
literal|"AGC"
block|,
literal|"AI"
block|,
literal|"AIQ"
block|,
literal|"ALU"
block|,
literal|"AMPS"
block|,
literal|"AM"
block|,
literal|"AND"
block|,
literal|"ANOVA"
block|,
literal|"ANSI"
block|,
literal|"API"
block|,
literal|"ARMA"
block|,
literal|"ARQ"
block|,
literal|"ASIC"
block|,
literal|"ASIP"
block|,
literal|"ASK"
block|,
literal|"ATE"
block|,
literal|"ATM"
block|,
literal|"ATP"
block|,
literal|"ATPG"
block|,
literal|"ATSC"
block|,
literal|"ATV"
block|,
literal|"AVC"
block|,
literal|"AWGN"
block|,
literal|"Alamouti"
block|,
literal|"AlGaAs"
block|,
literal|"AlGaN"
block|,
literal|"AlN"
block|,
literal|"Altera"
block|,
literal|"BCD"
block|,
literal|"BCH"
block|,
literal|"BCJR"
block|,
literal|"BDD"
block|,
literal|"BER"
block|,
literal|"BGA"
block|,
literal|"BIBO"
block|,
literal|"BIST"
block|,
literal|"BJT"
block|,
literal|"BLAST"
block|,
literal|"BPSK"
block|,
literal|"BP"
block|,
literal|"BT"
block|,
literal|"Baum"
block|,
literal|"Bayes"
block|,
literal|"Berlekamp"
block|,
literal|"Bessel"
block|,
literal|"BiCMOS"
block|,
literal|"Blackwell"
block|,
literal|"Bluetooth"
block|,
literal|"Booth"
block|,
literal|"Bose"
block|,
literal|"Bragg"
block|,
literal|"Butterworth"
block|,
literal|"CAD"
block|,
literal|"CAS"
block|,
literal|"CATV"
block|,
literal|"CBR"
block|,
literal|"CCD"
block|,
literal|"CDMA"
block|,
literal|"CD"
block|,
literal|"CDC"
block|,
literal|"CDR"
block|,
literal|"CERN"
block|,
literal|"CFAR"
block|,
literal|"CIC"
block|,
literal|"CLB"
block|,
literal|"CMFB"
block|,
literal|"CMOS"
block|,
literal|"CMRR"
block|,
literal|"CMS"
block|,
literal|"CNF"
block|,
literal|"CNN"
block|,
literal|"CO2"
block|,
literal|"CORDIC"
block|,
literal|"COTS"
block|,
literal|"CP"
block|,
literal|"CPE"
block|,
literal|"CPM"
block|,
literal|"CPLD"
block|,
literal|"CPU"
block|,
literal|"CRC"
block|,
literal|"CRM"
block|,
literal|"CSD"
block|,
literal|"CSE"
block|,
literal|"CSIT"
block|,
literal|"CSI"
block|,
literal|"CSMA"
block|,
literal|"CSP"
block|,
literal|"CT"
block|,
literal|"CUDA"
block|,
literal|"CW"
block|,
literal|"Carlo"
block|,
literal|"Cauer"
block|,
literal|"Chebychev"
block|,
literal|"Chebyshev"
block|,
literal|"Chien"
block|,
literal|"Cooley"
block|,
literal|"D/A"
block|,
literal|"DAB"
block|,
literal|"DAC"
block|,
literal|"DBMS"
block|,
literal|"DCT"
block|,
literal|"DC"
block|,
literal|"DDC"
block|,
literal|"DDFS"
block|,
literal|"DDR"
block|,
literal|"DDSM"
block|,
literal|"DDS"
block|,
literal|"DDoS"
block|,
literal|"DECT"
block|,
literal|"DEM"
block|,
literal|"DFE"
block|,
literal|"DFT"
block|,
literal|"DGPS"
block|,
literal|"DHT"
block|,
literal|"DIMM"
block|,
literal|"DLL"
block|,
literal|"DMT"
block|,
literal|"DNA"
block|,
literal|"DNL"
block|,
literal|"DOA"
block|,
literal|"DOCSIS"
block|,
literal|"DOI"
block|,
literal|"DPSK"
block|,
literal|"DRAM"
block|,
literal|"DRM"
block|,
literal|"DS"
block|,
literal|"DSP"
block|,
literal|"DST"
block|,
literal|"DTMB"
block|,
literal|"DTTB"
block|,
literal|"DTV"
block|,
literal|"DUC"
block|,
literal|"DVB-C"
block|,
literal|"DVB-H"
block|,
literal|"DVB-S"
block|,
literal|"DVB-T"
block|,
literal|"DVB"
block|,
literal|"DVD"
block|,
literal|"DVFS"
block|,
literal|"DVI"
block|,
literal|"DWT"
block|,
literal|"Dadda"
block|,
literal|"Daubechies"
block|,
literal|"Doppler"
block|,
literal|"ECC"
block|,
literal|"ECG"
block|,
literal|"ECL"
block|,
literal|"EDGE"
block|,
literal|"EEG"
block|,
literal|"EJB"
block|,
literal|"EKF"
block|,
literal|"EKG"
block|,
literal|"EMC"
block|,
literal|"EMG"
block|,
literal|"EMI"
block|,
literal|"ENOB"
block|,
literal|"EPROM"
block|,
literal|"ESD"
block|,
literal|"ESPRIT"
block|,
literal|"EV"
block|,
literal|"EVM"
block|,
literal|"Einstein"
block|,
literal|"Euclid"
block|,
literal|"Euler"
block|,
literal|"FBAR"
block|,
literal|"FDE"
block|,
literal|"FDI"
block|,
literal|"FDMA"
block|,
literal|"FDTD"
block|,
literal|"FEC"
block|,
literal|"FEM"
block|,
literal|"FER"
block|,
literal|"FET"
block|,
literal|"FFT"
block|,
literal|"FIFO"
block|,
literal|"FIR"
block|,
literal|"FMCW"
block|,
literal|"FM"
block|,
literal|"FP"
block|,
literal|"FPAA"
block|,
literal|"FPGA"
block|,
literal|"FPU"
block|,
literal|"FRM"
block|,
literal|"FSK"
block|,
literal|"FSM"
block|,
literal|"FTTC"
block|,
literal|"FTTH"
block|,
literal|"Farrow"
block|,
literal|"FinFET"
block|,
literal|"Fokker"
block|,
literal|"Fourier"
block|,
literal|"Frobenius"
block|,
literal|"GALS"
block|,
literal|"GCM"
block|,
literal|"GF"
block|,
literal|"GIS"
block|,
literal|"GM"
block|,
literal|"GMD"
block|,
literal|"GNSS"
block|,
literal|"GNU"
block|,
literal|"GPGPU"
block|,
literal|"GPRS"
block|,
literal|"GPS"
block|,
literal|"GPU"
block|,
literal|"GSM"
block|,
literal|"GSPS"
block|,
literal|"GaAs"
block|,
literal|"GaN"
block|,
literal|"Gabor"
block|,
literal|"Galileo"
block|,
literal|"Galois"
block|,
literal|"Gauss"
block|,
literal|"Gram-Schmidt"
block|,
literal|"H.264"
block|,
literal|"HARQ"
block|,
literal|"HBM"
block|,
literal|"HBT"
block|,
literal|"HDMI"
block|,
literal|"HDTV"
block|,
literal|"HD"
block|,
literal|"HDD"
block|,
literal|"HDI"
block|,
literal|"HDL"
block|,
literal|"HEMT"
block|,
literal|"HEVC"
block|,
literal|"HF"
block|,
literal|"HMM"
block|,
literal|"HPC"
block|,
literal|"HRV"
block|,
literal|"HSPA"
block|,
literal|"HSDPA"
block|,
literal|"HTML"
block|,
literal|"HVAC"
block|,
literal|"HVDC"
block|,
literal|"HW"
block|,
literal|"Haar"
block|,
literal|"Hadamard"
block|,
literal|"Hamming"
block|,
literal|"Hankel"
block|,
literal|"Hartley"
block|,
literal|"Hermit"
block|,
comment|// Hermitian, Hermite, Hermit
literal|"Hilbert"
block|,
literal|"I-V"
block|,
literal|"I/O"
block|,
literal|"I/Q"
block|,
literal|"IBM"
block|,
literal|"ICA"
block|,
literal|"IC"
block|,
literal|"ICI"
block|,
literal|"ICT"
block|,
literal|"IDCT"
block|,
literal|"IDFT"
block|,
literal|"IEC"
block|,
literal|"IEE"
block|,
literal|"IEEE"
block|,
literal|"IET"
block|,
literal|"IFFT"
block|,
literal|"IFIR"
block|,
literal|"IF"
block|,
literal|"IGBT"
block|,
literal|"II-VI"
block|,
literal|"II/VI"
block|,
literal|"III-V"
block|,
literal|"III/V"
block|,
literal|"IIR"
block|,
literal|"ILP"
block|,
literal|"IMDCT"
block|,
literal|"IMDST"
block|,
literal|"IMT-A"
block|,
comment|// IMT-Advanced
literal|"IMU"
block|,
literal|"INL"
block|,
literal|"IO"
block|,
literal|"IPTV"
block|,
literal|"IP"
block|,
literal|"IQ"
block|,
literal|"IR"
block|,
literal|"IS-95"
block|,
literal|"ISDN"
block|,
literal|"ISI"
block|,
literal|"ISM"
block|,
literal|"ISO"
block|,
literal|"ISS"
block|,
literal|"ITE"
block|,
literal|"InGaAs"
block|,
literal|"InGaN"
block|,
literal|"InP"
block|,
literal|"InSAR"
block|,
literal|"J2EE"
block|,
literal|"JEDEC"
block|,
literal|"JFET"
block|,
literal|"JIT"
block|,
literal|"JPEG"
block|,
literal|"JTAG"
block|,
literal|"Josephson"
block|,
literal|"Kalman"
block|,
literal|"Karatsuba"
block|,
literal|"LAN"
block|,
literal|"LC"
block|,
literal|"LCD"
block|,
literal|"LCG"
block|,
literal|"LDA"
block|,
literal|"LDD"
block|,
literal|"LDI"
block|,
literal|"LDPC"
block|,
literal|"LED"
block|,
literal|"LFSR"
block|,
literal|"LHC"
block|,
literal|"LIDAR"
block|,
literal|"LiDAR"
block|,
literal|"LLC"
block|,
literal|"LLR"
block|,
literal|"LMDS"
block|,
literal|"LMI"
block|,
literal|"LMS"
block|,
literal|"LNA"
block|,
literal|"LNS"
block|,
literal|"LS"
block|,
literal|"LSB"
block|,
literal|"LTCC"
block|,
literal|"LTE-A"
block|,
literal|"LTE"
block|,
literal|"LTI"
block|,
literal|"LUT"
block|,
literal|"LVDC"
block|,
literal|"LVDS"
block|,
literal|"LZW"
block|,
literal|"Laplace"
block|,
literal|"Lempel"
block|,
literal|"Lyapunov"
block|,
literal|"M2M"
block|,
literal|"MAC"
block|,
literal|"MANET"
block|,
literal|"MAP"
block|,
literal|"MASH"
block|,
literal|"MBE"
block|,
literal|"MC"
block|,
literal|"MCE"
block|,
literal|"MCM"
block|,
literal|"MCMC"
block|,
literal|"MCU"
block|,
literal|"MDCT"
block|,
literal|"MDST"
block|,
literal|"MEMS"
block|,
literal|"MESFET"
block|,
literal|"MILP"
block|,
literal|"MIMO"
block|,
literal|"MISO"
block|,
literal|"MIT"
block|,
literal|"ML"
block|,
literal|"MLSD"
block|,
literal|"MMIC"
block|,
literal|"MMS"
block|,
literal|"MMSE"
block|,
literal|"MOSFET"
block|,
literal|"MOS"
block|,
literal|"MP3"
block|,
literal|"MPC"
block|,
literal|"MPEG"
block|,
literal|"MPI"
block|,
literal|"MPPC"
block|,
literal|"MPSoC"
block|,
literal|"MQW"
block|,
literal|"MRC"
block|,
literal|"MRI"
block|,
literal|"MRTD"
block|,
literal|"MSB"
block|,
literal|"MSD"
block|,
literal|"MSE"
block|,
literal|"MSPS"
block|,
literal|"MTCMOS"
block|,
literal|"MTD"
block|,
literal|"MTI"
block|,
literal|"MV"
block|,
literal|"MVC"
block|,
literal|"Maclaurin"
block|,
literal|"Manchester"
block|,
literal|"Markov"
block|,
literal|"Massey"
block|,
literal|"Maxwell"
block|,
literal|"McClellan"
block|,
literal|"Miller"
block|,
literal|"Monte"
block|,
literal|"Montgomery"
block|,
literal|"Msps"
block|,
literal|"NAND"
block|,
literal|"NOR"
block|,
literal|"NF"
block|,
literal|"NMOS"
block|,
literal|"NRZ"
block|,
literal|"NTSC"
block|,
literal|"Newton"
block|,
literal|"NoC"
block|,
literal|"Nyquist"
block|,
literal|"OCR"
block|,
literal|"OFDMA"
block|,
literal|"OFDM"
block|,
literal|"OLED"
block|,
literal|"OOP"
block|,
literal|"OR"
block|,
literal|"OSR"
block|,
literal|"OTA-C"
block|,
literal|"OTA"
block|,
literal|"Ofman"
block|,
literal|"P2P"
block|,
literal|"PAL"
block|,
literal|"PAM"
block|,
literal|"PAPR"
block|,
literal|"PBGA"
block|,
literal|"PCA"
block|,
literal|"PCB"
block|,
literal|"PCI"
block|,
literal|"PCM"
block|,
literal|"PCMCIA"
block|,
literal|"PC"
block|,
literal|"PDF"
block|,
literal|"PDP"
block|,
literal|"PET"
block|,
literal|"PFA"
block|,
literal|"PHD"
block|,
literal|"PHY"
block|,
literal|"PIC"
block|,
literal|"PID"
block|,
literal|"PIN"
block|,
literal|"PLC"
block|,
literal|"PLL"
block|,
literal|"PMOS"
block|,
literal|"PN"
block|,
literal|"PON"
block|,
literal|"POTS"
block|,
literal|"PQFP"
block|,
literal|"PRPG"
block|,
literal|"PSK"
block|,
literal|"PSNR"
block|,
literal|"PSO"
block|,
literal|"PSRR"
block|,
literal|"PTL"
block|,
literal|"PV"
block|,
literal|"PWB"
block|,
literal|"PWM"
block|,
literal|"Parks-McClellan"
block|,
comment|// Only Parks will give lots of incorrect hits
literal|"Pb"
block|,
literal|"Planck"
block|,
literal|"PowerPC"
block|,
literal|"QAM"
block|,
literal|"QC-LDPC"
block|,
literal|"QCA"
block|,
literal|"QDI"
block|,
literal|"QFP"
block|,
literal|"QMF"
block|,
literal|"QNN"
block|,
literal|"QO"
block|,
literal|"QP"
block|,
literal|"QPSK"
block|,
literal|"QR"
block|,
literal|"QRD"
block|,
literal|"QRS"
block|,
literal|"QoS"
block|,
literal|"RAD"
block|,
literal|"RAKE"
block|,
literal|"RAM"
block|,
literal|"RBF"
block|,
literal|"RC"
block|,
literal|"RCS"
block|,
literal|"RFDAC"
block|,
literal|"RFE"
block|,
literal|"RFIC"
block|,
literal|"RFID"
block|,
literal|"RF"
block|,
literal|"RFS"
block|,
literal|"RISC"
block|,
literal|"RLC"
block|,
literal|"RLS"
block|,
literal|"RMS"
block|,
literal|"RNS"
block|,
literal|"ROM"
block|,
literal|"RRC"
block|,
literal|"RS"
block|,
literal|"RSA"
block|,
literal|"RT"
block|,
literal|"RTL"
block|,
literal|"RTOS"
block|,
literal|"RX"
block|,
literal|"RZ"
block|,
literal|"Rao"
block|,
literal|"Raphson"
block|,
literal|"Rayleigh"
block|,
literal|"Reed"
block|,
literal|"Remez"
block|,
literal|"Riccati"
block|,
literal|"SAR"
block|,
literal|"SAT"
block|,
literal|"SAW"
block|,
literal|"SC"
block|,
literal|"SDF"
block|,
literal|"SDH"
block|,
literal|"SDP"
block|,
literal|"SDRAM"
block|,
literal|"SDR"
block|,
literal|"SDRE"
block|,
literal|"SEU"
block|,
literal|"SFDR"
block|,
literal|"SIC"
block|,
literal|"SIMD"
block|,
literal|"SIMO"
block|,
literal|"SINAD"
block|,
literal|"SINR"
block|,
literal|"SINS"
block|,
literal|"SISO"
block|,
literal|"SLAM"
block|,
literal|"SMD"
block|,
literal|"SME"
block|,
literal|"SMS"
block|,
literal|"SNDR"
block|,
literal|"SNR"
block|,
literal|"SOCP"
block|,
literal|"SOI"
block|,
literal|"SONET"
block|,
literal|"SPS"
block|,
literal|"SPT"
block|,
literal|"SRAM"
block|,
literal|"SQL"
block|,
literal|"SQNR"
block|,
literal|"STBC"
block|,
literal|"SVD"
block|,
literal|"SVM"
block|,
literal|"SW"
block|,
literal|"Schottky"
block|,
literal|"SerDes"
block|,
literal|"Shannon"
block|,
literal|"SiC"
block|,
literal|"SiGe"
block|,
literal|"SoC"
block|,
literal|"SoP"
block|,
literal|"Solomon"
block|,
literal|"SystemC"
block|,
literal|"TCP"
block|,
literal|"TDC"
block|,
literal|"TDM"
block|,
literal|"TDMA"
block|,
literal|"TDS"
block|,
literal|"TETRA"
block|,
literal|"TFT"
block|,
literal|"THD"
block|,
literal|"THz"
block|,
literal|"TLM"
block|,
literal|"TLP"
block|,
literal|"TPG"
block|,
literal|"TQFP"
block|,
literal|"TSOP"
block|,
literal|"TSPC"
block|,
literal|"TTL"
block|,
literal|"TV"
block|,
literal|"TX"
block|,
literal|"Taylor"
block|,
literal|"Toeplitz"
block|,
literal|"Tukey"
block|,
literal|"UAV"
block|,
literal|"UHF"
block|,
literal|"UKF"
block|,
literal|"ULSI"
block|,
literal|"UML"
block|,
literal|"UMTS"
block|,
literal|"UPS"
block|,
literal|"USB"
block|,
literal|"USRP"
block|,
literal|"UV"
block|,
literal|"UWB"
block|,
literal|"V-BLAST"
block|,
literal|"VBLAST"
block|,
literal|"VBR"
block|,
literal|"VCO"
block|,
literal|"VDD"
block|,
literal|"VDSL"
block|,
literal|"VGA"
block|,
literal|"VHDL"
block|,
literal|"VHF"
block|,
literal|"VLIW"
block|,
literal|"VLSI"
block|,
literal|"VOD"
block|,
literal|"VQ"
block|,
literal|"VSB"
block|,
literal|"VTOL"
block|,
literal|"Vdd"
block|,
literal|"Verilog-AMS"
block|,
literal|"Verilog-A"
block|,
literal|"Verilog"
block|,
literal|"Viterbi"
block|,
literal|"VoD"
block|,
literal|"VoIP"
block|,
literal|"Volterra"
block|,
literal|"WCDMA"
block|,
literal|"WDF"
block|,
literal|"WDM"
block|,
literal|"WFTA"
block|,
literal|"WLAN"
block|,
literal|"WLS"
block|,
literal|"WPAN"
block|,
literal|"WSN"
block|,
literal|"WWW"
block|,
literal|"Wallace"
block|,
literal|"Watt"
block|,
literal|"Welch"
block|,
literal|"WiFi"
block|,
literal|"WiMAX"
block|,
literal|"Wiener"
block|,
literal|"Winograd"
block|,
literal|"XML"
block|,
literal|"XNOR"
block|,
literal|"XOR"
block|,
literal|"Xilinx"
block|,
literal|"ZCS"
block|,
literal|"ZF"
block|,
literal|"ZVS"
block|,
literal|"ZigBee"
block|,
literal|"Ziv"
block|,
literal|"xDSL"
block|}
decl_stmt|;
comment|// Weekdays and months
DECL|field|wordListDayMonth
specifier|public
name|String
index|[]
name|wordListDayMonth
init|=
operator|new
name|String
index|[]
block|{
literal|"Monday"
block|,
literal|"Tuesday"
block|,
literal|"Wednesday"
block|,
literal|"Thursday"
block|,
literal|"Friday"
block|,
literal|"Saturday"
block|,
literal|"Sunday"
block|,
literal|"January"
block|,
literal|"February"
block|,
literal|"March"
block|,
literal|"April"
block|,
literal|"May"
block|,
literal|"June"
block|,
literal|"July"
block|,
literal|"August"
block|,
literal|"September"
block|,
literal|"October"
block|,
literal|"November"
block|,
literal|"December"
block|}
decl_stmt|;
DECL|field|wordListCountries
specifier|private
name|String
index|[]
name|wordListCountries
init|=
operator|new
name|String
index|[]
block|{
literal|"Andorra"
block|,
literal|"United Arab Emirates"
block|,
literal|"UAE"
block|,
literal|"Afghanistan"
block|,
literal|"Antigua and Barbuda"
block|,
literal|"Anguilla"
block|,
literal|"Albania"
block|,
literal|"Armenia"
block|,
literal|"Netherlands Antilles"
block|,
literal|"Angola"
block|,
literal|"Antarctica"
block|,
literal|"Argentina"
block|,
literal|"American Samoa"
block|,
literal|"Austria"
block|,
literal|"Australia"
block|,
literal|"Aruba"
block|,
literal|"Aland Islands"
block|,
literal|"Aland"
block|,
literal|"Azerbaijan"
block|,
literal|"Bosnia and Herzegovina"
block|,
literal|"Barbados"
block|,
literal|"Bangladesh"
block|,
literal|"Belgium"
block|,
literal|"Burkina Faso"
block|,
literal|"Bulgaria"
block|,
literal|"Bahrain"
block|,
literal|"Burundi"
block|,
literal|"Benin"
block|,
literal|"Saint Barthelemy"
block|,
literal|"Bermuda"
block|,
literal|"Brunei"
block|,
literal|"Bolivia"
block|,
literal|"British Antarctic Territory"
block|,
literal|"Brazil"
block|,
literal|"Bahamas"
block|,
literal|"Bhutan"
block|,
literal|"Bouvet Island"
block|,
literal|"Botswana"
block|,
literal|"Belarus"
block|,
literal|"Belize"
block|,
literal|"Canada"
block|,
literal|"Cocos Islands"
block|,
literal|"Keeling Islands"
block|,
literal|"Congo"
block|,
literal|"Congo - Kinshasa"
block|,
literal|"Central African Republic"
block|,
literal|"Congo - Brazzaville"
block|,
literal|"Democratic Republic of Congo"
block|,
literal|"Switzerland"
block|,
literal|"Cote d'Ivoire"
block|,
literal|"Cook Islands"
block|,
literal|"Chile"
block|,
literal|"Cameroon"
block|,
literal|"China"
block|,
literal|"Colombia"
block|,
literal|"Costa Rica"
block|,
literal|"Serbia and Montenegro"
block|,
literal|"Canton and Enderbury Islands"
block|,
literal|"Cuba"
block|,
literal|"Cape Verde"
block|,
literal|"Christmas Island"
block|,
literal|"Cyprus"
block|,
literal|"Czech Republic"
block|,
literal|"East Germany"
block|,
literal|"Germany"
block|,
literal|"Djibouti"
block|,
literal|"Denmark"
block|,
literal|"Dominica"
block|,
literal|"Dominican Republic"
block|,
literal|"Algeria"
block|,
literal|"Ecuador"
block|,
literal|"Estonia"
block|,
literal|"Egypt"
block|,
literal|"Western Sahara"
block|,
literal|"Eritrea"
block|,
literal|"Spain"
block|,
literal|"Ethiopia"
block|,
literal|"Finland"
block|,
literal|"Fiji"
block|,
literal|"Falkland Islands"
block|,
literal|"Micronesia"
block|,
literal|"Faroe Islands"
block|,
literal|"French Southern and Antarctic Territories"
block|,
literal|"France"
block|,
literal|"Metropolitan France"
block|,
literal|"Gabon"
block|,
literal|"United Kingdom"
block|,
literal|"Great Britain"
block|,
literal|"Britain"
block|,
literal|"England"
block|,
literal|"Wales"
block|,
literal|"Scotland"
block|,
literal|"Northern Ireland"
block|,
literal|"Grenada"
block|,
literal|"Georgia"
block|,
literal|"French Guiana"
block|,
literal|"Guernsey"
block|,
literal|"Ghana"
block|,
literal|"Gibraltar"
block|,
literal|"Greenland"
block|,
literal|"Gambia"
block|,
literal|"Guinea"
block|,
literal|"Guadeloupe"
block|,
literal|"Equatorial Guinea"
block|,
literal|"Greece"
block|,
literal|"South Georgia and the South Sandwich Islands"
block|,
literal|"Guatemala"
block|,
literal|"Guam"
block|,
literal|"Guinea-Bissau"
block|,
literal|"Guyana"
block|,
literal|"Hong Kong SAR China"
block|,
literal|"Heard Island and McDonald Islands"
block|,
literal|"Honduras"
block|,
literal|"Croatia"
block|,
literal|"Haiti"
block|,
literal|"Hungary"
block|,
literal|"Indonesia"
block|,
literal|"Ireland"
block|,
literal|"Israel"
block|,
literal|"Isle of Man"
block|,
literal|"India"
block|,
literal|"British Indian Ocean Territory"
block|,
literal|"Iraq"
block|,
literal|"Iran"
block|,
literal|"Iceland"
block|,
literal|"Italy"
block|,
literal|"Jersey"
block|,
literal|"Jamaica"
block|,
literal|"Jordan"
block|,
literal|"Japan"
block|,
literal|"Johnston Island"
block|,
literal|"Kenya"
block|,
literal|"Kyrgyzstan"
block|,
literal|"Cambodia"
block|,
literal|"Kiribati"
block|,
literal|"Comoros"
block|,
literal|"Saint Kitts and Nevis"
block|,
literal|"North Korea"
block|,
literal|"South Korea"
block|,
literal|"Kuwait"
block|,
literal|"Cayman Islands"
block|,
literal|"Kazakhstan"
block|,
literal|"Laos"
block|,
literal|"Lebanon"
block|,
literal|"Saint Lucia"
block|,
literal|"Liechtenstein"
block|,
literal|"Sri Lanka"
block|,
literal|"Liberia"
block|,
literal|"Lesotho"
block|,
literal|"Lithuania"
block|,
literal|"Luxembourg"
block|,
literal|"Latvia"
block|,
literal|"Libya"
block|,
literal|"Morocco"
block|,
literal|"Monaco"
block|,
literal|"Moldova"
block|,
literal|"Montenegro"
block|,
literal|"Saint Martin"
block|,
literal|"Madagascar"
block|,
literal|"Marshall Islands"
block|,
literal|"Midway Islands"
block|,
literal|"Macedonia"
block|,
literal|"Mali"
block|,
literal|"Myanmar"
block|,
literal|"Burma"
block|,
literal|"Mongolia"
block|,
literal|"Macau SAR China"
block|,
literal|"Northern Mariana Islands"
block|,
literal|"Martinique"
block|,
literal|"Mauritania"
block|,
literal|"Montserrat"
block|,
literal|"Malta"
block|,
literal|"Mauritius"
block|,
literal|"Maldives"
block|,
literal|"Malawi"
block|,
literal|"Mexico"
block|,
literal|"Malaysia"
block|,
literal|"Mozambique"
block|,
literal|"Namibia"
block|,
literal|"New Caledonia"
block|,
literal|"Niger"
block|,
literal|"Norfolk Island"
block|,
literal|"Nigeria"
block|,
literal|"Nicaragua"
block|,
literal|"Netherlands"
block|,
literal|"Norway"
block|,
literal|"Nepal"
block|,
literal|"Dronning Maud Land"
block|,
literal|"Nauru"
block|,
literal|"Neutral Zone"
block|,
literal|"Niue"
block|,
literal|"New Zealand"
block|,
literal|"Oman"
block|,
literal|"Panama"
block|,
literal|"Pacific Islands Trust Territory"
block|,
literal|"Peru"
block|,
literal|"French Polynesia"
block|,
literal|"Papua New Guinea"
block|,
literal|"Philippines"
block|,
literal|"Pakistan"
block|,
literal|"Poland"
block|,
literal|"Saint Pierre and Miquelon"
block|,
literal|"Pitcairn Islands"
block|,
literal|"Puerto Rico"
block|,
literal|"Palestinian Territories"
block|,
literal|"Portugal"
block|,
literal|"U.S. Miscellaneous Pacific Islands"
block|,
literal|"Palau"
block|,
literal|"Paraguay"
block|,
literal|"Panama Canal Zone"
block|,
literal|"Qatar"
block|,
literal|"Reunion"
block|,
literal|"Romania"
block|,
literal|"Serbia"
block|,
literal|"Russia"
block|,
literal|"Rwanda"
block|,
literal|"Saudi Arabia"
block|,
literal|"Solomon Islands"
block|,
literal|"Seychelles"
block|,
literal|"Sudan"
block|,
literal|"Sweden"
block|,
literal|"Singapore"
block|,
literal|"Saint Helena"
block|,
literal|"Slovenia"
block|,
literal|"Svalbard and Jan Mayen"
block|,
literal|"Svalbard"
block|,
literal|"Jan Mayen"
block|,
literal|"Slovakia"
block|,
literal|"Sierra Leone"
block|,
literal|"San Marino"
block|,
literal|"Senegal"
block|,
literal|"Somalia"
block|,
literal|"Suriname"
block|,
literal|"Sao Tome and Principe"
block|,
literal|"Union of Soviet Socialist Republics"
block|,
literal|"USSR"
block|,
literal|"El Salvador"
block|,
literal|"Syria"
block|,
literal|"Swaziland"
block|,
literal|"Turks and Caicos Islands"
block|,
literal|"Chad"
block|,
literal|"French Southern Territories"
block|,
literal|"Togo"
block|,
literal|"Thailand"
block|,
literal|"Tajikistan"
block|,
literal|"Tokelau"
block|,
literal|"Timor-Leste"
block|,
literal|"Turkmenistan"
block|,
literal|"Tunisia"
block|,
literal|"Tonga"
block|,
literal|"Turkey"
block|,
literal|"Trinidad and Tobago"
block|,
literal|"Tuvalu"
block|,
literal|"Taiwan"
block|,
literal|"Tanzania"
block|,
literal|"Ukraine"
block|,
literal|"Uganda"
block|,
literal|"U.S. Minor Outlying Islands"
block|,
literal|"United States"
block|,
literal|"USA"
block|,
literal|"Uruguay"
block|,
literal|"Uzbekistan"
block|,
literal|"Vatican City"
block|,
literal|"Saint Vincent and the Grenadines"
block|,
literal|"North Vietnam"
block|,
literal|"Venezuela"
block|,
literal|"British Virgin Islands"
block|,
literal|"U.S. Virgin Islands"
block|,
literal|"Vietnam"
block|,
literal|"Vanuatu"
block|,
literal|"Wallis and Futuna"
block|,
literal|"Wake Island"
block|,
literal|"Samoa"
block|,
literal|"People's Democratic Republic of Yemen"
block|,
literal|"Yemen"
block|,
literal|"Yugoslavia"
block|,
literal|"Mayotte"
block|,
literal|"South Africa"
block|,
literal|"Zambia"
block|,
literal|"Zimbabwe"
block|,
literal|"Asia"
block|,
literal|"Oceania"
block|,
literal|"Europe"
block|,
literal|"America"
block|,
literal|"Africa"
block|,
literal|"South America"
block|,
literal|"North America"
block|}
decl_stmt|;
comment|// List of all keyword lists
DECL|field|allLists
specifier|private
name|String
index|[]
index|[]
name|allLists
init|=
operator|new
name|String
index|[]
index|[]
block|{
name|wordListIEEEXplore
block|,
name|wordListDayMonth
block|,
name|wordListCountries
block|}
decl_stmt|;
DECL|field|genericLists
specifier|private
name|String
index|[]
index|[]
name|genericLists
init|=
operator|new
name|String
index|[]
index|[]
block|{
name|wordListDayMonth
block|,
name|wordListCountries
block|}
decl_stmt|;
DECL|method|CaseKeeperList ()
specifier|public
name|CaseKeeperList
parameter_list|()
block|{        }
empty_stmt|;
comment|/* Return all lists concatenated     * Can be done faster once deciding on Java 6.0     * see: http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java     */
DECL|method|getAll ()
specifier|public
name|String
index|[]
name|getAll
parameter_list|()
block|{
name|int
name|lengh
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
index|[]
name|array
range|:
name|allLists
control|)
block|{
name|lengh
operator|+=
name|array
operator|.
name|length
expr_stmt|;
block|}
name|String
index|[]
name|result
init|=
operator|new
name|String
index|[
name|lengh
index|]
decl_stmt|;
name|int
name|pos
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
index|[]
name|array
range|:
name|allLists
control|)
block|{
for|for
control|(
name|String
name|element
range|:
name|array
control|)
block|{
name|result
index|[
name|pos
index|]
operator|=
name|element
expr_stmt|;
name|pos
operator|++
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

