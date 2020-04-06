package ns.tcphack;

/*
Jan van Zwol s2159732
Brand Hauser s2234823
 */

import java.util.Arrays;

class MyTcpHandler extends ns.tcphack.TcpHandler {
	int[] txpkt;
	int[] lastSeq;

	public static void main(String[] args) {
		new MyTcpHandler();
	}

	public MyTcpHandler() {
		super();

		boolean done = false;
		boolean connected = false;

		// array of bytes in which we're going to build our packet:
		txpkt = new int[64];		// 40 bytes long for now, may need to expand this later

		txpkt[0] = 0x60;	// first byte of the IPv6 header contains version number in upper nibble
		// fill in the rest of the packet yourself...:
		txpkt[1] = 0x00; //second half of traffic class = 0 and 1st half flow label = 0
		txpkt[2] = 0x00; //flow label 2 and 3
		txpkt[3] = 0x00; //flow label 4 and 5 =0
		txpkt[4] = 0x00; //payload length 1 and 2 of 4
		txpkt[5] = 0x18; //payload length 3 and 4 of 4 = TBD
		txpkt[6] = 0xfd; //next header 1 and 2 of 2
		txpkt[7] = 0x78; //hop limit 1 and 2 of 2 = 120 seconds
		txpkt[8] = 0x20; //source address 1 and 2 of 32
		txpkt[9] = 0x01; //source address 3 and 4 of 32
		txpkt[10] = 0x06; //source address 5 and 6 of 32
		txpkt[11] = 0x7c; //source address 7 and 8 of 32
		txpkt[12] = 0x25; //source address 9 and 10 of 32
		txpkt[13] = 0x64; //source address 11 and 12 of 32
		txpkt[14] = 0xa1; //source address 13 and 14 of 32
		txpkt[15] = 0x83; //source address 15 and 16 of 32
		txpkt[16] = 0x14; //source address 17 and 18 of 32
		txpkt[17] = 0x93; //source address 19 and 20 of 32
		txpkt[18] = 0x49; //source address 21 and 22 of 32
		txpkt[19] = 0x5c; //source address 23 and 24 of 32
		txpkt[20] = 0x17; //source address 25 and 26 of 32
		txpkt[21] = 0x4a; //source address 27 and 28 of 32
		txpkt[22] = 0x69; //source address 29 and 30 of 32
		txpkt[23] = 0xcd; //source address 31 and 32 of 32
		txpkt[24] = 0x20; //destination address 1 and 2 of 32
		txpkt[25] = 0x01; //destination address 3 and 4 of 32
		txpkt[26] = 0x06; //destination address 5 and 6 of 32
		txpkt[27] = 0x10; //destination address 7 and 8 of 32
		txpkt[28] = 0x19; //destination address 9 and 10 of 32
		txpkt[29] = 0x08; //destination address 11 and 12 of 32
		txpkt[30] = 0xff; //destination address 13 and 14 of 32
		txpkt[31] = 0x02; //destination address 15 and 16 of 32
		txpkt[32] = 0x25; //destination address 17 and 18 of 32
		txpkt[33] = 0xd2; //destination address 19 and 20 of 32
		txpkt[34] = 0x04; //destination address 21 and 22 of 32
		txpkt[35] = 0x43; //destination address 23 and 24 of 32
		txpkt[36] = 0xe4; //destination address 25 and 26 of 32
		txpkt[37] = 0x1c; //destination address 27 and 28 of 32
		txpkt[38] = 0x64; //destination address 29 and 30 of 32
		txpkt[39] = 0x97; //destination address 31 and 32 of 32
		txpkt[40] = 0xd2; //TCP header - source port 1 and 2 of 4
		txpkt[41] = 0x14; //TCP header - source port 3 and 4 of 4
		txpkt[42] = 0x1e; //TCP header - destination port 1 and 2 of 4
		txpkt[43] = 0x1e; //TCP header - destination port 3 and 4 of 4
		txpkt[44] = 0x00; //TCP header - sequence number 1 and 2 of 8
		txpkt[45] = 0x00; //TCP header - sequence number 3 and 4 of 8
		txpkt[46] = 0x00; //TCP header - sequence number 5 and 6 of 8
		txpkt[47] = 0x01; //TCP header - sequence number 7 and 8 of 8
		txpkt[48] = 0x00; //TCP header - ack number 1 and 2 of 8
		txpkt[49] = 0x00; //TCP header - ack number 3 and 4 of 8
		txpkt[50] = 0x00; //TCP header - ack number 5 and 6 of 8
		txpkt[51] = 0x00; //TCP header - ack number 7 and 8 of 8
		txpkt[52] = 0x60; //TCP header - offset 1 of 1 and reserved 1 of 1.5
		txpkt[53] = 0x02; //TCP header - reserved 1.5 of 1.5 and control .5 and 1 of 1.5
		txpkt[54] = 0xfa; //TCP header - window 1 and 2 of 4
		txpkt[55] = 0xf0; //TCP header - window 3 and 4 of 4
		txpkt[56] = 0x00; //TCP header - checksum 1 and 2 of 4
		txpkt[57] = 0x00; //TCP header - checksum 3 and 4 of 4
		txpkt[58] = 0x00; //TCP header - urgent pointer 1 and 2 of 4
		txpkt[59] = 0x00; //TCP header - urgent pointer 3 and 4 of 4
		txpkt[60] = 0x00; //TCP header - options 1 and 2 of 3
		txpkt[61] = 0x00; //TCP header - options pointer 3 and padding 1
		txpkt[62] = 0x00; //TCP header - options pointer 3 and padding 1
		txpkt[63] = 0xb0; //TCP header - options pointer 3 and padding 1

		int[] destAdd = extractNum(txpkt, 24, 39);
		int[] destPort = extractNum(txpkt, 42, 43);
		int[] sourceAdd = extractNum(txpkt, 8, 23);
		int[] sourcePort = extractNum(txpkt, 40, 41);
		int[] sequenceNum = extractNum(txpkt, 44, 47);




		this.sendData(txpkt);	// send the packet

		int[] recSrcAdd;
		int[] recDstAdd;
		int[] recSrcPort;
		int[] recDstPort;
		int[] recSeqNum;
		int[] recFlag;
		int[] recAckNum;
		int counter = 0;
		boolean fin = false;

		while (!done) {
			// check for reception of a packet, but wait at most 500 ms:
			int[] rxpkt = this.receiveData(500);
			if (rxpkt.length==0) {
				// nothing has been received yet
				System.out.println("Nothing...");
				counter ++;
				if (counter == 10 && !fin){
					sendFin(sequenceNum);
					fin = true;
				}
				continue;
			}

			// something has been received
			int len=rxpkt.length;

			// print the received bytes:
			int i;
			System.out.print("Received "+len+" bytes: ");
			for (i=0;i<len;i++) System.out.print(rxpkt[i]+" ");
			System.out.println("");
			counter = 0;

			for (i=0;i<len; i++){
			}

			recSrcAdd = extractNum(rxpkt, 8, 23);
			recDstAdd = extractNum(rxpkt, 24, 39);
			recSrcPort = extractNum(rxpkt, 40, 41);
			recDstPort = extractNum(rxpkt, 42, 43);
			recSeqNum = extractNum(rxpkt, 44, 47);
			recFlag = extractNum(rxpkt, 52, 53);
			recAckNum = extractNum(rxpkt, 48, 51);
			lastSeq = recSeqNum;

			if (Arrays.equals(recDstAdd, sourceAdd) && Arrays.equals(recSrcAdd, destAdd)
					&& Arrays.equals(recDstPort, sourcePort) && Arrays.equals(recSrcPort, destPort)) {
				if (recFlag[0] == 96 && recFlag[1] == 18) { //received a Syn Ack
					sendAck(sequenceNum, recSeqNum);
					sendGet(txpkt);
				} else if (recFlag[1]  == 17 && fin == true){ // received a Fin ack
					sendAck(sequenceNum, recSeqNum);
					done = true;
				} else if (recFlag[1]  == 17){ // received a Fin ack
					sendFinAck(recAckNum, recSeqNum);
					done = true;
				} else if (recFlag[1] % 16 == 1){ // received a Fin
					sendFinAck(sequenceNum, recSeqNum);
					done = true;
					fin = true;
				}

			}


		}


	}
	public void sendAck(int[] seq, int[] ack){
		ack = add1(ack);
		overwriteNum(txpkt, ack, 48);
		txpkt[53] = 16;
		seq = add1(seq);
		overwriteNum(txpkt, seq, 44);
		this.sendData(txpkt);
	}

	public void sendGet(int[] list) {
		String getRequestString = "GET /HTTP/1.0\r\n";
		byte[] getRequest = getRequestString.getBytes();
		int[] getpkt = new int[list.length + getRequest.length];
		int[] payload = extractNum(list, 4, 5);
		for (int i = 0; i < list.length; i++) getpkt[i] = list[i];
		for (int i = 0; i < getRequest.length; i++) {
			getpkt[i + list.length] = getRequest[i];
			add1(payload);
		}
		overwriteNum(getpkt, payload, 4);
		getpkt[53] = 0x18;
		this.sendData(getpkt);
	}


	public void sendFinAck(int[] seq, int[] ack){
		ack = add1(ack);
		overwriteNum(txpkt, ack, 48);
		txpkt[53] = 17;
		//seq = add1(seq);
		overwriteNum(txpkt, seq, 44);
		this.sendData(txpkt);
	}

	public void sendFin(int[] seq){
		int[] ack = new int[]{00,00,00,00};
		overwriteNum(txpkt, ack, 48);
		txpkt[53] = 01;
		seq = add1(seq);
		overwriteNum(txpkt, seq, 44);
		this.sendData(txpkt);
	}

	public int[] extractNum(int[] list, int start, int end){
		int[] result = new int[end+1 - start];
		int j = 0;
		for(int i = start; i <= end; i++){
			result[j] = list[i];
			j++;
		}
		return result;
	}

	public void overwriteNum(int[] list, int[] newNum, int start){
		int j = start;
		for (int i: newNum){
			list[j] = i;
			j++;
		}
	}

	public int[] add1(int[] numList){
		if (numList[numList.length - 1] == 255){
			numList[numList.length - 1] = 0;
			numList[numList.length - 2] += 1;
		} else {
			numList[numList.length - 1] +=1;
		}
		return numList;
	}

	public void printList(int[] list){
		for (int i: list){
			System.out.print(i);
		}
		System.out.println();
	}
}
