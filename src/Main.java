import java.io.*;
import java.math.BigInteger;
import java.util.*;
//library (10-15)
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            if (i % 2 == 1) {
                sum += sc.nextInt();
            } else {
                sc.nextInt();
            }
        }
        System.out.println(sum);
    }
    public static class FastScanner {//FastScanner
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1<<16];
        private int ptr = 0;
        private int buflen = 0;
        private boolean hasNextByte() {
            if (ptr < buflen) {
                return true;
            }else{
                ptr = 0;
                try {
                    buflen = in.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (buflen <= 0) {
                    return false;
                }
            }
            return true;
        }
        private int readByte() { if (hasNextByte()) return buffer[ptr++]; else return -1;}
        private static boolean isPrintableChar(int c) { return 33 <= c && c <= 126;}
        private void skipUnprintable() { while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;}
        public boolean hasNext() { skipUnprintable(); return hasNextByte();}
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while(isPrintableChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }
        public long nextLong() {
            if (!hasNext()) throw new NoSuchElementException();
            long n = 0;
            boolean minus = false;
            int b = readByte();
            if (b == '-') {
                minus = true;
                b = readByte();
            }
            if (b < '0' || '9' < b) {
                throw new NumberFormatException();
            }
            while(true){
                if ('0' <= b && b <= '9') {
                    n *= 10;
                    n += b - '0';
                }else if(b == -1 || !isPrintableChar(b)){
                    return minus ? -n : n;
                }else{
                    throw new NumberFormatException();
                }
                b = readByte();
            }
        }

        public int nextInt(){
            return (int)nextLong();
        }

        public double nextDouble() {return Double.parseDouble(next());}
    }
    //String s = "abcdefghijklmnopqrstuvwxyz";
    public static boolean[] allPrime(int N){//エラトステネスのふるい
        boolean[] Deleted = new boolean[N+1];
        Deleted[1] = true;
        for(int i=2;i*i<=N;i++){
            if(Deleted[i]){
                continue;
            }
            for(int j=i*i;j <= N;j += i){
                Deleted[j] = true;
            }
        }
        return Deleted;
    }
    //smallest prime factor(最小素因数) : 2以上の整数nに対して、nの素因数のうち最小のもの
    public static int[] spf(int N) {
        boolean[] deleted = new boolean[N + 1];
        deleted[1] = true;
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i * i <= N; i++) {
            if (deleted[i]) {
                continue;
            }

            for (int j = i * i; j <= N; j+= i) {
                deleted[j] = true;
            }
        }

        for (int i = 2; i <= N; i++) {//2~Nまでのものをもとめる
            if (!deleted[i]) {
                primes.add(i);
            }
        }

        int[] spf = new int[N + 1];
        for (int k = primes.size() - 1; k >= 0; k--) {
            int p = primes.get(k);

            for (int n = p; n <= N; n += p) {
                spf[n] = p;
            }
        }
        return spf;
    }

    //素因数分解
    public static ArrayList<Integer> PrimeFactorization(int N) {
        int[] spf = spf(N+1);
        ArrayList<Integer> factors = new ArrayList<>();

        while (N > 1) {
            int p = spf[N];
            factors.add(p);
            N = N / p;
        }
        return factors;
    }

    public static double sita(double d){
        return Math.PI*d/180;
        //原点からの移動
        //double x = a*Math.cos(sita)-b*Math.sin(sita);
        //double y = a*Math.sin(sita)+b*Math.cos(sita);
    }
    //割り算で余りを取りたいとき。(ただし、modは素数限定)
    public static long modDiv(long a,long b,long mod){
        return (a * Power(b,mod-2L,mod)) % mod;
    }
    public static long Power(long a,long b,long mod){
        long p = a;
        long ans = 1;
        for(int i=0;i<30;i++){
            long wari = (1L<<i);
            if((b/wari)%2L == 1L){
                ans = (ans * p) % mod;
            }
            p = (p*p)%mod;
        }
        return ans;
    }
    //bit全探索のテンプレ
    public static void bit(int N){
        for(int i=0;i<(1<<N);i++){
            boolean[] color = new boolean[N];
            for(int j=0;j<N;j++){
                int mask = 1 << j;
                if((i & mask) != 0){
                    color[j] = true;
                }else{
                    color[j] = false;
                }
            }
        }
    }
    public static int[] reverse(int left,int right,int[] ans){
        while(left < right) {
            int temp = ans[left];
            ans[left] = ans[right];
            ans[right] = temp;
            left++;
            right--;
        }
        return ans;
    }
    //三角形の面積(0から反時計回り)(-になったら、一つの角が180度以上である)
    public static int calc_S(int x0,int y0,int x1,int y1,int x2,int y2){
        int s = (int) (0.5*(x0*y1 + x1*y2 + x2*y0 - y0*x1 - y1*x2 - y2*x0));
        return s;
    }
    //二次元配列の回転
    public static boolean[][] kaiten(boolean[][] S,int N,int M){//配列を90度回転
        boolean[][] rote = new boolean[M][N];
        for (int i=0;i<N;i++) {
            for (int j=0;j<M;j++) {
                //右回転
                rote[j][N-1-i] = S[i][j];
                //左回転
                //rote[M - 1 - j][i] = S[i][j];
            }
        }
        return rote;
    }
    //nCrの計算
    public static long comb(int n,int r){
        if(n == r||r == 0){
            return 1;
        }else if(n < r){
            return 0;
        }else{
            long result = 1;
            for(int i=1;i<=r;i++){
                result = result * (n - i + 1)/i;
            }
            return result;
        }
    }
    //modを取る(余りが負になるとき)
    static long mod(long x,long m){
        return ((x % m) + m) %m;
    }
    //BigInteger省略用
    static BigInteger bi(long x){
        return new BigInteger(String.valueOf(x));
    }
    //回文判定
    public static boolean validPalindrome(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
    //LとRの区間をTreeMapで管理する
    public static void lToRQueryAddInterval(TreeMap<Long, Long> map, long L, long R) {
        Long key = map.floorKey(L);
        if (key != null && map.get(key) + 1 >= L) {
            L = key;
            R = Math.max(R, map.get(key));
            map.remove(key);
        }
        key = map.ceilingKey(L);

        while (key != null && key <= R + 1) {
            R = Math.max(R, map.get(key));
            map.remove(key);
            key = map.ceilingKey(L);
        }

        map.put(L, R);
    }
    //配列のbinary searchを行う
    // arr[i] >= key となる最初の位置(arr[i]を含むぞ
    static int lowerBound(long[] arr, long key) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r)/2;
            if (arr[mid] >= key) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    // arr[i] > key となる最初の位置(arr[i]を含まない
    static int upperBound(long[] arr, long key) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = (l + r)/2;
            if (arr[mid] > key) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    //回文の集合をo(n^2)で返す関数
    static Set<String> enumeratePalindromes(String s) {
        Set<String> result = new HashSet<>();

        for (int center = 0; center < 2 * s.length() - 1; center++) {
            int left = center / 2;
            int right = left + center % 2;

            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                result.add(s.substring(left, right + 1));
                left--;
                right++;
            }
        }

        return result;
    }

    //ダイクストラ法とレコード
    record Edge(int to,long cost){}
    static long[] dijkstra(HashMap<Integer,ArrayList<Edge>> graph,int start){
        int N = 20;//実際に使うときは消してstatic N;
        long[] dist = new long[N+1];
        for(int i=1;i<=N;i++){
            dist[i] = Long.MAX_VALUE;
        }
        dist[start] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparing(Edge::cost));
        pq.offer(new Edge(start,0));
        while(!pq.isEmpty()){
            Edge cur = pq.poll();
            int v = cur.to();
            long d = cur.cost();
            //System.out.println(v + " " + d);
            if(d <= dist[v]){
                for(Edge e:graph.get(v)){
                    long newDist = d + e.cost();
                    if(newDist < dist[e.to]){
                        dist[e.to()] = newDist;
                        pq.offer(new Edge(e.to(),newDist));
                    }
                }
            }
        }
        return dist;
    }
}
class UnionFind {
    int[] parent;
    int[] size;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];

        for(int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;   // 最初は自分自身だけなのでサイズ1
        }
    }

    // 根を求める（経路圧縮あり）
    public int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    // 同じ集合かどうか
    public boolean same(int x, int y) {
        return find(x) == find(y);
    }

    // 連結する
    public boolean unite(int x, int y) {
        int rx = find(x);
        int ry = find(y);

        if(rx == ry) return false;  // すでに同じ集合

        // サイズの大きい方に小さい方をくっつける
        if(size[rx] < size[ry]) {
            int tmp = rx;
            rx = ry;
            ry = tmp;
        }

        parent[ry] = rx;
        size[rx] += size[ry];

        return true;
    }

    // 属する連結成分のサイズを返す
    public int getSize(int x) {
        return size[find(x)];
    }
}
//重み付きUnionFind
class WeightedUnionFind {
    int[] parent;
    int[] size;
    int[] diff_weight;
    public WeightedUnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        diff_weight = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1; // 最初は自分自身だけなのでサイズ1
            diff_weight[i] = 0;
        }
    }

    // 根を求める（経路圧縮あり）
    public int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        int p = parent[x];
        parent[x] = find(p);
        diff_weight[x] += diff_weight[p];
        return parent[x];
    }
    public int weight(int x){
        find(x);
        return diff_weight[x];
    }
    public int diff(int x,int y){
        return weight(y) - weight(x);
    }
    // 同じ集合かどうか
    public boolean same(int x, int y) {
        return find(x) == find(y);
    }

    // 連結する
    public boolean unite(int x, int y,int w) {
        int rx = find(x);
        int ry = find(y);
        int wx = diff_weight[x];
        int wy = diff_weight[y];
        int d = w + wx - wy;
        if(rx == ry) return false;  // すでに同じ集合

        // サイズの大きい方に小さい方をくっつける
        if(size[rx] < size[ry]) {
            int tmp = rx;
            rx = ry;
            ry = tmp;
            d = -d;
        }

        parent[ry] = rx;
        size[rx] += size[ry];
        diff_weight[ry] = d;
        return true;
    }

    // 属する連結成分のサイズを返す
    public int getSize(int x) {
        return size[find(x)];
    }
}
class MyPoint{
    int x;
    int y;
    MyPoint(int i,int j){
        this.x = i;
        this.y = j;
    }
    //これを定義すると自動でComparatorを使える
    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        MyPoint p = (MyPoint)o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
}
//BellmanFord法(負の辺が存在するときの最短経路を求める)
class BellmanFord{
    int V,E,r;//頂点数V,辺の数E,始点r
    //SからTへの長さDの有効辺
    int[] S;//
    int[] T;
    long[] D;
    long[] dist;
    BellmanFord(int V,int E,int r){
        this.V = V;
        this.E = E;
        this.r = r;
    }
    public void setEffectiveEdge(int[] S,int[] T,long[] D){
        this.S = S;
        this.T = T;
        this.D = D;
    }
    public void getDist(){
        dist = new long[V];
        Arrays.fill(dist,Long.MAX_VALUE);
        dist[r] = 0;
        for(int i=0;i<=V-1;i++){
            for(int j=0;j<E;j++){
                if(dist[S[j]] != Long.MAX_VALUE){
                    dist[T[j]] = Math.min(dist[T[j]],dist[S[j]] + D[j]);
                }
            }
        }
        //負の閉路の確認(n回目を行ってn-1回目と変わっていたら負の経路が存在している)
        long[] nTimeDist = new long[V];
        System.arraycopy(dist, 0, nTimeDist, 0, V);
        for(int j=0;j<E;j++){
            if(nTimeDist[S[j]] != Long.MAX_VALUE){
                nTimeDist[T[j]] = Math.min(nTimeDist[T[j]],nTimeDist[S[j]] + D[j]);
            }
        }
        for(int i=0;i<V;i++){
            if(nTimeDist[i] != dist[i]){
                System.out.println("NEGATIVE CYCLE");
                return;
            }
        }
        for(int i=0;i<V;i++){
            if(dist[i] == Long.MAX_VALUE){
                System.out.println("INF");
            }else{
                System.out.println(dist[i]);
            }
        }
    }
}
class DirectedMST {//最小全域有向木
    static class Edge {
        int from, to;
        long cost;

        Edge(int f, int t, long c) {
            from = f;
            to = t;
            cost = c;
        }
    }
    List<Edge> edges;
    DirectedMST(){
        edges = new ArrayList<>();
    }
    static final long INF = Long.MAX_VALUE / 4;
    public void addEdge(int from,int to,long cost){
        edges.add(new Edge(from,to,cost));
    }
    // n: 頂点数, root: 根, edges: 辺リスト
    public long edmonds(int n, int root) {
        long result = 0;

        while (true) {

            long[] minIn = new long[n];
            int[] pre = new int[n];
            Arrays.fill(minIn, INF);
            Arrays.fill(pre, -1);

            // ① 最小入辺を選ぶ
            for (Edge e : edges) {
                if (e.from != e.to && e.cost < minIn[e.to]) {
                    minIn[e.to] = e.cost;
                    pre[e.to] = e.from;
                }
            }

            minIn[root] = 0;

            // 到達不能チェック
            for (int i = 0; i < n; i++) {
                if (i == root) continue;
                if (minIn[i] == INF) return -1; // MSTなし
                result += minIn[i];
            }

            // ② サイクル検出・縮約
            int[] id = new int[n];
            Arrays.fill(id, -1);
            int[] vis = new int[n];
            int idx = 0;

            for (int i = 0; i < n; i++) {
                int v = i;

                while (v != root && vis[v] == 0) {
                    vis[v] = i + 1;
                    v = pre[v];
                }

                if (v != root && vis[v] == i + 1) {
                    while (id[v] == -1) {
                        id[v] = idx;
                        v = pre[v];
                    }
                    idx++;
                }
            }

            if (idx == 0) break; // サイクルなし

            for (int i = 0; i < n; i++) {
                if (id[i] == -1) id[i] = idx++;
            }

            // ③ グラフ縮約
            List<Edge> newEdges = new ArrayList<>();

            for (Edge e : edges) {
                int u = id[e.from];
                int v = id[e.to];
                long w = e.cost - minIn[e.to];

                if (u != v) {
                    newEdges.add(new Edge(u, v, w));
                }
            }

            n = idx;
            root = id[root];
            edges = newEdges;
        }

        return result;
    }
}

class WarshallFloyd {
    private static final long INF = Long.MAX_VALUE / 4;

    private final int n;
    private final long[][] dist;

    public WarshallFloyd(int n) {
        this.n = n;
        dist = new long[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
    }

    // 辺を追加（重複する辺がある場合は小さい方を採用）
    public void addEdge(int from, int to, long cost) {
        dist[from][to] = Math.min(dist[from][to], cost);
    }

    // 無向辺
    public void addUndirectedEdge(int u, int v, long cost) {
        addEdge(u, v, cost);
        addEdge(v, u, cost);
    }

    // 最短路計算
    public void build() {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (dist[i][k] == INF) continue;
                for (int j = 0; j < n; j++) {
                    if (dist[k][j] == INF) continue;
                    long nd = dist[i][k] + dist[k][j];
                    if (nd < dist[i][j]) {
                        dist[i][j] = nd;
                    }
                }
            }
        }
    }

    // u→vの最短距離
    public long getDist(int u, int v) {
        return dist[u][v];
    }

    // 到達可能か
    public boolean reachable(int u, int v) {
        return dist[u][v] != INF;
    }

    // 負閉路が存在するか
    public boolean hasNegativeCycle() {
        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) return true;
        }
        return false;
    }

    // 必要なら距離行列を取得
    public long[][] getMatrix() {
        return dist;
    }
//    WarshallFloyd wf = new WarshallFloyd(N);
//
//    for (...) {
//            wf.addEdge(u, v, w);          // 有向
//            // wf.addUndirectedEdge(u, v, w); // 無向ならこちら
//        }
//
//    wf.build();
//
//        long d = wf.getDist(0, 5);
//
//    if (wf.reachable(0, 5)) {
//            System.out.println(d);
//        }
//
//    if (wf.hasNegativeCycle()) {
//            System.out.println("Negative Cycle");
//        }
}
//class FastScanner {
//    private final InputStream in;
//    private final byte[] buffer = new byte[1 << 16];
//    private int pos = 0, len = 0;
//
//    public FastScanner(InputStream in) {
//        this.in = in;
//    }
//
//    private int read() throws IOException {
//        if (this.pos >= this.len) {
//            this.len = this.in.read(buffer);
//            this.pos = 0;
//            if (this.len <= 0) {
//                return -1;
//            }
//        }
//        return buffer[this.pos++];
//    }
//
//    long nextLong() throws IOException {
//        int c;
//        while((c = read()) <= ' ') {
//            if (c == -1) {
//                return Long.MIN_VALUE;
//            }
//        }
//        boolean minus = false;
//        if (c == '-') {
//            minus = true;
//            c = read();
//        }
//        long val = c - '0';
//        while ((c = read()) > ' ') {
//            val = val * 10 + (c - '0');
//        }
//        return minus ? -val : val;
//    }
//
//    int nextInt() throws IOException {
//        return (int)nextLong();
//    }
//
//    String next() throws IOException  {
//        int c;
//        while ((c = read()) <= ' ') {
//            if (c == -1) {
//                return null;
//            }
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append((char)c);
//        while ((c = read()) > ' '){
//            sb.append((char)c);
//        }
//        return sb.toString();
//    }
//
//    double nextDouble() throws IOException {
//        return Double.parseDouble(next());
//    }
//
//    float nextFloat() throws IOException {
//        return Float.parseFloat(next());
//    }
//}

//class segmentTree {
//    long[] segmentTree;
//    int n;
//
//    public segmentTree(long[] array) {
//        n = array.length;
//        segmentTree = new long[4 * n];
//        build(array, 0, n - 1, 1);
//    }
//
//    public void print(){
//        System.out.println(Arrays.toString(segmentTree));
//    }
//
//    private void build(long[] array, int start, int end, int index) {
//        if (start == end) {
//            segmentTree[index] = array[start];
//        } else {
//            int mid = (start + end) / 2;
//            build(array, start, mid, 2 * index);
//            build(array, mid + 1, end, 2 * index + 1);
//            segmentTree[index] = segmentTree[2 * index] + segmentTree[2 * index + 1];
//        }
//    }
//
//    public long sum(int L, int R) {
//        return sum(1, 0, n - 1, L, R);
//    }
//
//    private long sum(int index, int start, int end, int L, int R) {
//        if (R < start || end < L) {
//            return 0;
//        }
//        if (L <= start && end <= R) {
//            return segmentTree[index];
//        }
//        int mid = (start + end) / 2;
//        return sum(2 * index, start, mid, L, R) + sum(2 * index + 1, mid + 1, end, L, R);
//    }
//
//    public void update(int pos, int newValue) {
//        long diff = newValue - sum(pos, pos);
//        update(1, 0, n - 1, pos, diff);
//    }
//
//    private void update(int index, int start, int end, int pos, long diff) {
//        if (pos < start || pos > end) {
//            return;
//        }
//        segmentTree[index] += diff;
//        if (start != end) {
//            int mid = (start + end) / 2;
//            update(2 * index, start, mid, pos, diff);
//            update(2 * index + 1, mid + 1, end, pos, diff);
//        }
//    }
//}
class LazySegmentTree{
    int size;
    long[] data;
    long[] lazy;
    LazySegmentTree(long[] A) {
        int aSize = A.length;
        size = 1;
        while (size < aSize) {
            size *= 2;
        }
        data = new long[size * 2 - 1];
        lazy = new long[size * 2 - 1];
        Arrays.fill(data, Long.MAX_VALUE);
        Arrays.fill(lazy, 0);
        for (int i = 0; i < A.length; i++) {
            data[i + size - 1] = A[i];
        }

        for (int i = size - 2; i >= 0; i--) {
            data[i] = Math.min(data[2 * i + 1], data[2 * i + 2]);
        }
    }

    public void push(int i) {
        long x = lazy[i];
        data[2 * i + 1] += x;
        lazy[2 * i + 1] += x;
        data[2 * i + 2] += x;
        lazy[2 * i + 2] += x;
        lazy[i] = 0;
    }

    public void apply(int L, int R, long x) {
        apply_rec(0, 0, size, L, R, x);
    }

    private void apply_rec(int i, int node_L, int node_R, int L, int R, long x) {
        if (node_R <= L || R <= node_L) {
            return;
        }
        if (L <= node_L && node_R <= R) {
            data[i] += x;
            lazy[i] += x;
            return;
        }

        push(i);
        int m = (node_L + node_R) / 2;
        apply_rec(2 * i + 1, node_L, m, L, R, x);
        apply_rec(2 * i + 2, m, node_R, L, R, x);
        data[i] = Math.min(data[2 * i + 1], data[2 * i + 2]);
    }

    private long prod_rec(int i, int node_L, int node_R, int L, int R) {
        if (node_R <= L || R <= node_L) {
            return Long.MAX_VALUE;
        }
        if (L <= node_L && node_R <= R) {
            return data[i];
        }
        push(i);
        int m = (node_L + node_R) / 2;
        long x = prod_rec(2 * i + 1, node_L, m, L, R);
        long y = prod_rec(2 * i + 2, m, node_R, L, R);
        return Math.min(x, y);
    }
    public long prod(int L, int R) {
        return prod_rec(0,0, size, L, R);
    }
}

class RMQ {
    private final int n;
    private final int[] tree;

    // 配列から構築
    public RMQ(int[] arr) {
        int size = 1;
        while (size < arr.length) {
            size <<= 1;
        }

        n = size;
        tree = new int[2 * n];

        // 最大値を扱うので、未使用部分は -∞
        for (int i = 0; i < 2 * n; i++) {
            tree[i] = Integer.MIN_VALUE;
        }

        // 葉に値をセット
        for (int i = 0; i < arr.length; i++) {
            tree[n + i] = arr[i];
        }

        // 下から構築
        for (int i = n - 1; i > 0; i--) {
            tree[i] = Math.max(tree[2 * i], tree[2 * i + 1]);
        }
    }

    // index の値を value に変更
    public void update(int index, int value) {
        int i = index + n;
        tree[i] = value;

        // 親を更新
        while (i > 1) {
            i >>= 1;
            tree[i] = Math.max(tree[2 * i], tree[2 * i + 1]);
        }
    }

    // 区間 [l, r) の最大値を取得
    public int query(int l, int r) {
        int res = Integer.MIN_VALUE;

        l += n;
        r += n;

        while (l < r) {
            // l が右の子なら、そのノードを採用
            if ((l & 1) == 1) {
                res = Math.max(res, tree[l]);
                l++;
            }

            // r が右の子なら、1つ左のノードを採用
            if ((r & 1) == 1) {
                r--;
                res = Math.max(res, tree[r]);
            }

            l >>= 1;
            r >>= 1;
        }

        return res;
    }
}

class RmQ{
    int size;
    long[] data;
    RmQ(long[] A) {
        int aSize = A.length;
        size = 1;
        while (size < aSize) {
            size *= 2;
        }
        data = new long[size * 2 - 1];
        Arrays.fill(data, Long.MAX_VALUE);
        for (int i = 0; i < A.length; i++) {
            data[i + size - 1] = A[i];
        }

        for (int i = size - 2; i >= 0; i--) {
            data[i] = Math.min(data[2 * i + 1], data[2 * i + 2]);
        }
    }
    //o(logN)
    public void update(int pos, long x) {
        pos = pos + size - 1;//sizeをN以上で最も最小の2^Nとしたとき
        data[pos] = x;
        while (pos > 0) {//上に上に順に更新する
            pos = (pos - 1) / 2;
            data[pos] = Math.min(data[pos * 2 + 1], data[pos * 2 + 2]);
        }
    }

    public long query(int l, int r) {
        return query(l, r, 0, size, 0);
    }

    //o(logN)
    //[l,r)は求めたい半開区間,[a,b)はセルに対応する半開区間,uは現在のセル番号
    private long query(int l, int r, int a, int b, int u) {
        if (r <= a || b <= l) {//aとbが一切含まれていない時
            return Long.MAX_VALUE;
        }
        if (l <= a && b <= r) {//aとbが完全に含まれている時
            return data[u];
        }
        int m = (a + b) / 2;//中心
        long answerL = query(l, r, a, m, u * 2 + 1);//左側[a, m)を見る
        long answerR = query(l, r, m, b, u * 2 + 2);
        return Math.min(answerL, answerR);
    }
}
class SegmentTree{
    int size;
    long[] data;
    int N;
    long sum;
    SegmentTree(long[] A) {
        int aSize = A.length;
        N = A.length;
        size = 1;
        while (size < aSize) {
            size *= 2;
        }
        data = new long[size * 2 - 1];
        Arrays.fill(data, 0);
        for (int i = 0; i < A.length; i++) {
            data[i + size - 1] = A[i];
        }

        for (int i = size - 2; i >= 0; i--) {
            data[i] = data[2 * i + 1] + data[2 * i + 2];
        }
    }

    public void update(int pos, long x) {
        pos = pos + size - 1;//sizeをN以上で最も最小の2^Nとしたとき
        data[pos] = x;
        while (pos > 0) {//上に上に順に更新する
            pos = (pos - 1) / 2;
            data[pos] = data[pos * 2 + 1] + data[pos * 2 + 2];
        }
    }

    public long query(int l, int r) {
        return query(l, r, 0, size, 0);
    }

    //o(logN)
    //[l,r)は求めたい半開区間,[a,b)はセルに対応する半開区間,uは現在のセル番号
    private long query(int l, int r, int a, int b, int u) {
        if (r <= a || b <= l) {//aとbが一切含まれていない時
            return 0;
        }
        if (l <= a && b <= r) {//aとbが完全に含まれている時
            return data[u];
        }
        int m = (a + b) / 2;//中心
        long answerL = query(l, r, a, m, u * 2 + 1);//左側[a, m)を見る
        long answerR = query(l, r, m, b, u * 2 + 2);
        return answerL + answerR;
    }
    //t以上で最小のものを探す(LからN)
    //見つからないときはNを返す
    private int maxRightRec(int i, int nodeL, int nodeR, int L, long t) {
        if (nodeR <= L) {
            return N;
        }
        if (L <= nodeL && sum + data[i] < t) {
            sum += data[i];
            return N;
        }

        if (nodeR - nodeL == 1) {
            return nodeL;
        }

        int m = (nodeL + nodeR) / 2;
        int x = maxRightRec(2 * i + 1, nodeL, m, L, t);
        if (x != N) {
            return x;
        }
        return maxRightRec(2 * i + 2, m, nodeR, L, t);
    }

    //[L,N)
    public int maxRight(int L, long t) {
        sum = 0;
        return maxRightRec(0, 0, size, L, t);
    }

    //条件を満たさなくなる位置の一つ右
    //見つからないときは0を返す
    public int minLeftRec(int i, int nodeL, int nodeR, int R, long t) {
        if (R <= nodeL) {
            return 0;
        }
        if (nodeR <= R && sum + data[i] < t) {
            sum += data[i];
            return 0;
        }
        if (nodeR - nodeL == 1) {
            return nodeR;
        }
        int m = (nodeL + nodeR) / 2;
        int x = minLeftRec(2 * i + 2, m, nodeR, R, t);
        if (x != 0) {
            return x;
        }
        return minLeftRec(2 * i + 1, nodeL, m, R, t);
    }
    //[0, R)
    //Rを固定して、区間の和がt以上になる最大の左端lを返す(1indexの左端lと一致する)
    public int minLeft(int R, long t) {
        sum = 0;
        return minLeftRec(0, 0, size, R, t);
    }
}

class DynamicSegTree {
    static class Node {
        long sum;
        Long lazySet = null;
        Node left, right;
    }

    Node root = new Node();
    final int N = (int)1e9; // 0 to N-1 の範囲

    private void push(Node node, int l, int r) {
        if (node.lazySet != null) {
            long v = node.lazySet;
            node.sum = v * (r - l + 1);

            if (l != r) { // 子が必要なときだけ作る
                if (node.left == null) node.left = new Node();
                if (node.right == null) node.right = new Node();
                node.left.lazySet = v;
                node.right.lazySet = v;
            }

            node.lazySet = null;
        }
    }

    public void update(int L, int R, long val) {
        update(root, 0, N - 1, L, R, val);
    }

    private void update(Node node, int l, int r, int L, int R, long val) {
        push(node, l, r);

        if (r < L || R < l) return;
        if (L <= l && r <= R) {
            node.lazySet = val;
            push(node, l, r);
            return;
        }

        int mid = (l + r) >> 1;
        if (node.left == null) node.left = new Node();
        if (node.right == null) node.right = new Node();

        update(node.left, l, mid, L, R, val);
        update(node.right, mid + 1, r, L, R, val);

        node.sum = node.left.sum + node.right.sum;
    }

    public long query(int L, int R) {
        return query(root, 0, N - 1, L, R);
    }

    private long query(Node node, int l, int r, int L, int R) {
        if (node == null) return 0;
        push(node, l, r);

        if (r < L || R < l) return 0;
        if (L <= l && r <= R) return node.sum;

        int mid = (l + r) >> 1;
        return query(node.left, l, mid, L, R)
                + query(node.right, mid + 1, r, L, R);
    }
}

//強連結成分分解(頂点xとyがお互いにいききできるかどうかを求める)
//お互いに行き来できる場合は同じグループとする
class SCC {
    static HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
    static HashMap<Integer,HashSet<Integer>> reverseGraph = new HashMap<>();
    static HashSet<Integer> visited = new HashSet<>();
    static ArrayList<Integer> order = new ArrayList<>();
    static HashMap<Integer,Integer> component = new HashMap<>();

    //1回目のdfs
    static void dfs1(int v){
        visited.add(v);

        for(int next:graph.get(v)){
            if(!visited.contains(next)){
                dfs1(next);
            }
        }

        order.add(v);
    }

    static void dfs2(int v,int id){
        component.put(v, id);
        for(int next:reverseGraph.get(v)){
            if(!component.containsKey(next)){
                dfs2(next,id);
            }
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();//頂点の数
        int M = sc.nextInt();//辺の数

        //初期化
        for(int i=1;i<=N;i++){
            graph.put(i,new HashSet<>());
            reverseGraph.put(i,new HashSet<>());
        }

        //グラフの追加
        for(int i=0;i<M;i++){
            int from = sc.nextInt();
            int to = sc.nextInt();

            graph.get(from).add(to);
            reverseGraph.get(to).add(from);
        }

        for(int i=1;i<=N;i++){
            if(!visited.contains(i)){
                dfs1(i);
            }
        }

        //逆順
        Collections.reverse(order);

        int sccId = 0;

        for(int v:order){
            if(!component.containsKey(v)){
                dfs2(v,sccId);
                sccId++;
            }
        }
        for(int i=1;i<=N;i++){
            System.out.println(i + "->" + component.get(i));
        }
    }
}

//最長増加部分列
class Lis {
    static ArrayList<Integer> LIS(int[] A){
        int N = A.length;

        int[] tail = new int[N];      // tail[len] = 長さlen+1のLISの末尾の値
        int[] tailIndex = new int[N]; // tail[len]が元配列の何番目か
        int[] prev = new int[N];      // 一つ前の要素の位置

        Arrays.fill(prev, -1);

        int len = 0;

        for(int i = 0; i < N; i++){
            int pos = lowerBound(tail,len,A[i]);

            tail[pos] = A[i];
            tailIndex[pos] = i;

            if(pos > 0){
                prev[i] = tailIndex[pos - 1];
            }

            if(pos == len){
                len++;
            }
        }

        ArrayList<Integer> lis = new ArrayList<>();

        int cur = tailIndex[len - 1];
        while(cur != -1){
            lis.add(A[cur]);
            cur = prev[cur];
        }

        Collections.reverse(lis);

        return lis;
    }
    static int lowerBound(int[] arr, int len, int key){
        int l = 0;
        int r = len;

        while(l < r){
            int mid = (l + r) / 2;

            if(arr[mid] >= key){
                r = mid;
            }else{
                l = mid + 1;
            }
        }

        return l;
    }
}
class dice {
    public static boolean sameDice(int dice1[],int dice2[]){
        for(int i=0;i<6;i++){
            int temp[] = dice1.clone();
            setTopFace(dice1[i], temp);
            for(int j=0;j<4;j++){
                if(Arrays.equals(temp, dice2))return true;
                int tmp = temp[1];
                temp[1] = temp[3];
                temp[3] = temp[4];
                temp[4] = temp[2];
                temp[2] = tmp;
            }
        }
        return false;
    }
    //    dice[0] // 上面 (Top)
//    dice[1] // 前面 (Front)
//    dice[2] // 左面 (Left)
//    dice[3] // 右面 (Right)
//    dice[4] // 後面 (Back)
//    dice[5] // 底面 (Bottom)
    public static void roll(char dir,int[] dice){
        int temp;
        if(dir == 'N'){
            temp = dice[0];
            dice[0] = dice[1];
            dice[1] = dice[5];
            dice[5] = dice[4];
            dice[4] = temp;
        }else if(dir == 'S'){
            temp = dice[0];
            dice[0] = dice[4];
            dice[4] = dice[5];
            dice[5] = dice[1];
            dice[1] = temp;
        }else if(dir == 'E'){
            temp = dice[0];
            dice[0] = dice[3];
            dice[3] = dice[5];
            dice[5] = dice[2];
            dice[2] = temp;
        }else if(dir == 'W'){
            temp = dice[0];
            dice[0] = dice[2];
            dice[2] = dice[5];
            dice[5] = dice[3];
            dice[3] = temp;
        }
    }
    public static int getFace(int num,int[] dice){
        return dice[num - 1];
    }
    public static void setTopFace(int top,int[] dice){
        if(top == dice[1]){
            roll('N',dice);
        }else if(top == dice[2]){
            roll('W',dice);
        }else if(top == dice[3]){
            roll('E',dice);
        }else if(top == dice[4]){
            roll('S',dice);
        }else if(top == dice[5]){
            roll('N',dice);
            roll('N',dice);
        }
    }
    public static void setFrontFace(int front,int[] dice){
        int temp;
        if (front == dice[2]) {
            temp = dice[1];
            dice[1] = dice[2];
            dice[2] = dice[4];
            dice[4] = dice[3];
            dice[3] = temp;
        } else if (front == dice[3]) {
            temp = dice[1];
            dice[1] = dice[3];
            dice[3] = dice[4];
            dice[4] = dice[2];
            dice[2] = temp;
        } else if (front == dice[4]) {
            temp = dice[1];
            dice[1] = dice[4];
            dice[4] = temp;
            temp = dice[2];
            dice[2] = dice[3];
            dice[3] = temp;
        }
    }
}
class SortedList<T extends Comparable<T>> {

    private static final int BLOCK_SIZE = 700;

    private final ArrayList<ArrayList<T>> blocks = new ArrayList<>();
    private int size = 0;

    // 要素数
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // k番目取得 (0-index)
    public T get(int k) {
        if (k < 0 || k >= size) {
            throw new IndexOutOfBoundsException();
        }
        //block毎にサイズを調べて求めたい場所がどこか調べる
        for (ArrayList<T> block : blocks) {
            if (k < block.size()) {
                return block.get(k);
            }
            k -= block.size();
        }

        throw new AssertionError();
    }

    // 追加
    public void add(T x) {
        if (blocks.isEmpty()) {
            ArrayList<T> block = new ArrayList<>();
            block.add(x);
            blocks.add(block);
            size++;
            return;
        }

        for (ArrayList<T> block : blocks) {
            //どのブロックに入れるか調べる
            if (x.compareTo(block.get(block.size() - 1)) <= 0) {

                int idx = lowerBound(block, x);
                block.add(idx, x);
                //blockが大きくなりすぎたら分割する
                if (block.size() > BLOCK_SIZE * 2) {
                    split(block);
                }

                size++;
                return;
            }
        }

        blocks.get(blocks.size() - 1).add(x);
        size++;
    }

    // 削除（1個）
    public boolean remove(T x) {

        for (int b = 0; b < blocks.size(); b++) {

            ArrayList<T> block = blocks.get(b);

            if (x.compareTo(block.get(block.size() - 1)) <= 0) {

                int idx = lowerBound(block, x);

                if (idx == block.size()) return false;

                if (!block.get(idx).equals(x)) return false;

                block.remove(idx);
                size--;

                if (block.isEmpty()) {
                    blocks.remove(b);
                }

                return true;
            }
        }

        return false;
    }

    // contains
    public boolean contains(T x) {

        for (ArrayList<T> block : blocks) {

            if (x.compareTo(block.get(block.size() - 1)) <= 0) {

                int idx = lowerBound(block, x);

                return idx < block.size() &&
                        block.get(idx).equals(x);
            }
        }

        return false;
    }

    // 最小
    public T first() {
        return blocks.get(0).get(0);
    }

    // 最大
    public T last() {
        ArrayList<T> block = blocks.get(blocks.size() - 1);
        return block.get(block.size() - 1);
    }

    // lower_bound
    public int lowerBound(T x) {

        int idx = 0;

        for (ArrayList<T> block : blocks) {

            if (x.compareTo(block.get(block.size() - 1)) <= 0) {

                return idx + lowerBound(block, x);
            }

            idx += block.size();
        }

        return size;
    }

    // upper_bound
    public int upperBound(T x) {

        int idx = 0;

        for (ArrayList<T> block : blocks) {

            if (x.compareTo(block.get(block.size() - 1)) < 0) {

                return idx + upperBound(block, x);
            }

            idx += block.size();
        }

        return size;
    }

    // ===== private =====

    private int lowerBound(ArrayList<T> block, T x) {

        int l = 0;
        int r = block.size();

        while (l < r) {

            int m = (l + r) >>> 1;

            if (block.get(m).compareTo(x) >= 0) {
                r = m;
            } else {
                l = m + 1;
            }
        }

        return l;
    }

    private int upperBound(ArrayList<T> block, T x) {

        int l = 0;
        int r = block.size();

        while (l < r) {

            int m = (l + r) >>> 1;

            if (block.get(m).compareTo(x) > 0) {
                r = m;
            } else {
                l = m + 1;
            }
        }

        return l;
    }

    private void split(ArrayList<T> block) {

        int mid = block.size() / 2;

        ArrayList<T> newBlock = new ArrayList<>();

        while (block.size() > mid) {
            newBlock.add(block.remove(mid));
        }

        int idx = blocks.indexOf(block);

        blocks.add(idx + 1, newBlock);
    }

    @Override
    public String toString() {
        return blocks.toString();
    }
}

class bitModels {
    int i = 85 & 15;//論理積(両方のbitが1の時だけ1)
    int j = 85 | 15;//論理和(どちらかが1なら1とする)
    int k = 85 ^ 15;//排他的論理和(どちらか1なら1、両方同じ数なら0)
    //排他的論理和は同じ数を足すと0になる
    int l = ~15;//反転(0は1に、1は0に)
    long n = 2 << 1;//左に1ビットシフト
    long m = 2 >> 1;//右に1ビットシフト
    BigInteger bi = new BigInteger("1101",2);//大きな数の2進数変換
    int b = Integer.parseInt("1101",2);//普通の2進数変換
}

class Permutation {
    static int N = 3;
    static int[] a = {1, 2, 3};
    static boolean[] used = new boolean[N];
    static int[] perm = new int[N];
    //static List<int[]> all = new ArrayList<>();
    static void dfs(int depth) {
        if (depth == N) {
            //System.out.println(Arrays.toString(perm));
            //all.add(perm.clone());
            return;
        }

        for (int i = 0; i < N; i++) {
            if (used[i]) continue;

            used[i] = true;
            perm[depth] = a[i];
            dfs(depth + 1);
            used[i] = false;
        }
    }
    //重複なしの全探索
//    Arrays.sort(a);
//
//    static void dfs(int depth) {
//        if (depth == N) {
//            // ここに処理を書く
//            System.out.println(new String(perm));
//            return;
//        }
//
//        for (int i = 0; i < N; i++) {
//
//            if (used[i]) continue;
//            //重複を飛ばす
//            if (i > 0 && a[i] == a[i - 1] && !used[i - 1]) continue;
//
//            used[i] = true;
//            perm[depth] = a[i];
//
//            dfs(depth + 1);
//
//            used[i] = false;
//        }
//    }
    public static void main(String[] args) {
        dfs(0);
    }
}

class shapes {
    public HashSet<Point2> rotate(HashSet<Point2> shape){//90度回転
        HashSet<Point2> shape2 = new HashSet<>();
        for(Point2 cur:shape){
            shape2.add(new Point2(cur.y(),-cur.x()));
        }
        return shape2;
    }
    record Point2(int x,int y){}
    //二次元配列の回転
    public static boolean[][] kaiten(boolean[][] S,int N,int M){//配列を90度回転
        boolean[][] rote = new boolean[M][N];
        for (int i=0;i<N;i++) {
            for (int j=0;j<M;j++) {
                //右回転
                rote[j][N-1-i] = S[i][j];
                //左回転
                //rote[M - 1 - j][i] = S[i][j];
            }
        }
        return rote;
    }

    //二次元配列のマスごとに回転
    public static boolean[][] kaiten2(boolean[][] S , int N){
        boolean[][] ans = new boolean[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                int d = Math.min(Math.min(i+1,j+1),Math.min(N-i,N-j));
                int ni = i;
                int nj = j;
                //90度時計回り
                for(int k=0;k<d%4;k++){
                    int ti = nj;
                    int tj = N - 1 - ni;
                    ni = ti;
                    nj = tj;
                }
                ans[ni][nj] = S[i][j];
            }
        }
        return ans;
    }
}