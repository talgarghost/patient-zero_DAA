# Assignment 4. Patient Zero

Student ID: 252955
Name: Tagay Talgar
Group: SE-2511

This file is the report. Fill it in as you go. How to start: `START_HERE.md`. The task: `docs/Assignment4.pdf`.

## Program output

The 9 lines your program prints on your data (`java -cp out Main`):

```
Mutations: 67
Shared core: 861
Marker hits: 6
First hit: 3637
Border length: 4
Shared words: 399
Tests needed: 36
Cheapest testing: 159
Longest chain: 26

```

My outbreak report (a screenshot of `output/outbreak.html`):

![outbreak](output/outbreak.png)

## Questions

Answer in your own words, two or three sentences each.

Mission 1. What does the cell d\[i\]\[j\] of your table mean, and how is it computed from other cells?
The cell `d[i][j]` represents the minimum edit distance (number of operations) required to turn the first $i$ letters of strain A into the first $j$ letters of strain B. It is computed by taking the minimum cost among three options: a deletion (`d[i-1][j] + 1`), an insertion (`d[i][j-1] + 1`), or a substitution/match (`d[i-1][j-1] + cost`, where cost is 0 if letters match and 1 if they differ).

Mission 2. How is the longest common subsequence different from the longest common substring? My Shared core is bigger than the number of letters that are the same at the same place: why?
A common substring requires the letters to be strictly consecutive with no gaps, whereas a subsequence allows characters to be separated as long as their relative order is maintained. Your Shared core is bigger because mutations (insertions and deletions) shift the positions of letters; LCS finds matches even if they are no longer at the exact same index `i` in both strings.

Mission 3. What does the prefix function tell you, and why does the pointer in the text never go back in KMP? What is the Border length of my marker and what does it mean?
The prefix function stores the length of the longest proper prefix of the pattern that is also a suffix up to that point. In KMP, the text pointer never goes back because the prefix array tells us exactly how much of the pattern we can safely skip without missing a potential match. The Border length of my marker is 4, which means the longest proper prefix of the marker that perfectly matches its end (suffix) is 4 letters long (e.g., "TTGG").

Mission 4. Why must you compare the letters after the hashes are equal? What happens to the cost of Rabin-Karp if many windows have the same hash?
You must compare the actual letters because of "hash collisions," where two completely different strings mathematically compute to the exact same hash value. If many windows share the same hash (e.g., if the hash modulo is too small), the algorithm frequently falls back to character-by-character comparison, increasing the cost from $O(n)$ up to $O(n \times k)$ in the worst case.

Mission 5. What are the two states of a person in your DP, and how do you get the answer for a person from its children? Why does a greedy "test the person with most links first" not always give the cheapest answer?
The two states for a person are the minimum cost if the person IS tested, and the minimum cost if the person is NOT tested. If a person is tested, we take the minimum of (tested, not tested) for each child; if the person is not tested, all of its children MUST be tested. A greedy approach fails because picking a locally "cheap" or "connected" person might force us into a corner where we later have to test multiple highly expensive children to cover the remaining links.

## AI use

The syllabus requires you to say which AI tools you used and for what. Write the tool (ChatGPT, Copilot, other) and what you asked it: explain an idea, find an error, review code. Write "none" if you did not use any. If you took code or text from a book or a website, give the source here.

* **Tool:** Gemini

* **What I asked:** I used Gemini to help write the implementations for the algorithms in `Strains.java`, `Marker.java`, and `Outbreak.java` (Edit distance, LCS, KMP, Rabin-Karp, and Tree DP). I also asked it to format my console output and Bench results into this `README.md` template and explain the theoretical concepts for the questions above.

## Timing (Mission 7)

Milliseconds, average of 3 runs, from `java -cp out Bench`.

| test | size | time | 
| ----- | ----- | ----- | 
| naive search | 20 000 / 40 000 / 80 000 | 24,3 / 64,6 / 128,3 | 
| KMP | 20 000 / 40 000 / 80 000 | 1,9 / 1,2 / 0,4 | 
| edit distance | 500 / 1000 / 2000 / 4000 | 5,6 / 7,2 / 14,6 / 58,4 | 

What do the numbers say about how the time grows? When you double the size, by how much does each time grow? Two or three sentences.
For the naive search, as the text size doubles, the time roughly doubles, showing a linear growth heavily impacted by mismatches. For the edit distance, when the string length doubles (e.g., from 2000 to 4000), the time quadruples (from 14.6 ms to 58.4 ms), which perfectly illustrates its $O(n \times m)$ quadratic complexity.

## Complexity

n is the length of the text (or of a strain), m the length of the pattern, k the length of the window, v the number of people. Fill in every algorithm you wrote.

| algorithm | time | space | assumptions | 
| ----- | ----- | ----- | ----- | 
| Edit distance | $O(n \times m)$ | $O(n \times m)$ |  | 
| LCS | $O(n \times m)$ | $O(n \times m)$ |  | 
| Prefix function and KMP | $O(n + m)$ | $O(m)$ | Alphabet size is constant. | 
| Rabin-Karp (shared words) | $O(n + m)$ | $O(m)$ | Assuming few hash collisions; otherwise $O(n \times k)$ worst case. | 
| Tree DP | $O(v)$ | $O(v)$ | Using an adjacency list where the number of edges $E = V - 1$. | 

## Before you submit

* The four files in `data` and the screenshot `output/outbreak.png` are in the repository.

* `java tools/Check.java` shows all examples right.

* This README is filled in, the program output and the section AI use included.

* The work is pushed to main (at least one commit per mission) and the repository is public.

* The link is attached in Moodle.