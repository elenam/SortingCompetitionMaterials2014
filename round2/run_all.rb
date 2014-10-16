
### the script for running the 2014 sorting competition 

### to pin to one core, run as: taskset -c 0 ruby run_all.rb


groups = 15 # number of groups (including group 0 which is the sample sorting)
loops = [10, 10, 10]
runs = 3
elements = [100000, 50000, 30000]
lambdas = [0.2, 5.5, 8.0] 
inFileNames = ["in3.txt", "in4.txt", "in5.txt"]

# loop over lambdas and elements

lambdas.length.times do |r| 
  # generate data, store it in a file
  resultsFile = "results#{r + 1}.txt"
  system("java DataGenerator #{inFileNames[r]} #{elements[r]} #{lambdas[r]}")
  system("echo 'Running lambda =  #{lambdas[r]} with #{elements[r]} elements, #{loops[r]} loops\n' >> #{resultsFile}")
 
  # run all groups
  groups.times do |j|
    system("echo 'Group#{j}\n'")
    system("echo '\n Group#{j}: \n' >> #{resultsFile}")
    runs.times do 
      system("java Group#{j} #{inFileNames[r]} outRun#{r + 1}Group#{j}.txt #{loops[r]} >> #{resultsFile}") 
    end
    system("echo 'Results of diff:\n' >> #{resultsFile}")
    system("diff --ignore-all-space --brief outRun#{r + 1}Group#{j}.txt outRun#{r + 1}Group0.txt >> #{resultsFile}")
  end
end


